package cn.cdqf.dmsjportal.service.impl;

import cn.cdqf.dmsjportal.common.Constant;
import cn.cdqf.dmsjportal.common.CustomException;
import cn.cdqf.dmsjportal.common.ResultResponse;
import cn.cdqf.dmsjportal.dao.DmsjProjectAttentionMapper;
import cn.cdqf.dmsjportal.entity.DmsjProjectAttentionKey;
import cn.cdqf.dmsjportal.entity.param.DmsjUserParam;
import cn.cdqf.dmsjportal.entity.param.UserAttentionParam;
import cn.cdqf.dmsjportal.service.AttentionService;
import cn.cdqf.dmsjportal.util.RedisUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttentionServiceImpl implements AttentionService {
    @Autowired
    @SuppressWarnings("all")
    private DmsjProjectAttentionMapper dmsjProjectAttentionMapper;
    @Override
    @Transactional
    public void add(String projectId, String userId) {
        if(null==projectId||"".equals(projectId)){
            throw new CustomException("项目id不能为空");
        }
        DmsjProjectAttentionKey build = DmsjProjectAttentionKey.builder().projectId(projectId).userId(userId).build();
        dmsjProjectAttentionMapper.insert(build);
        //放入redis集合中  这个地方不成功 会引起 缓存数据不一致
        redisUtils.rpush(Constant.DmsjUser.REDIS_USER_ATTENTION_PROJECT_IDS + userId, projectId);
        //当前项目的关注数应该+1
        redisUtils.hincr(Constant.Project.REDIS_PROJECT_ATTENTION_KEY + projectId,Constant.UserAttentionParam.REDIS_HASH_ATTENTION,1);
    }
    @Autowired
    private RedisUtils redisUtils;
    @Override
    public ResultResponse getAttentionByUser(String userId) {
        //先从redis中取数据 取用户的关注的项目id  list类型
        List<Object> objectList =redisUtils.lrange(Constant.DmsjUser.REDIS_USER_ATTENTION_PROJECT_IDS+userId,0,-1);
        List<UserAttentionParam> userAttentionParamList = new ArrayList<>();
        if(objectList==null){//redis中没有取到数据
            objectList = new ArrayList<>();
            List<String> projectIds = dmsjProjectAttentionMapper.queryProjectIdsByUserId(userId);
            //没有关注的
            if(projectIds==null){
                return ResultResponse.success();
            }
            for (String projectId : projectIds) {
                UserAttentionParam userAttentionParam = queryByProjectId(projectId);
                if(userAttentionParam!=null){
                    objectList.add(projectId);
                    userAttentionParamList.add(userAttentionParam);
                }
            }
            redisUtils.rpush(Constant.DmsjUser.REDIS_USER_ATTENTION_PROJECT_IDS+userId,objectList);
        }else{//说明当前用户关注的项目不为null
            for (Object o : objectList) {
                String projectId = (String)o;
                UserAttentionParam userAttentionParam = queryByProjectId(projectId);
                if(userAttentionParam!=null){
                    userAttentionParamList.add(userAttentionParam);
                }

            }
        }
        return ResultResponse.success(userAttentionParamList);
    }
    @Autowired
    private ObjectMapper objectMapper;
    public UserAttentionParam queryByProjectId(String projectId){
        Map<Object, Object> hmget = redisUtils.hmget(Constant.Project.REDIS_PROJECT_ATTENTION_KEY + projectId);
        //根据id没有查询到数据 就去数据库查询
        if(hmget==null||hmget.size()==0){
            UserAttentionParam userAttentionParam =  dmsjProjectAttentionMapper.queryAttentionParamById(projectId);
            if(userAttentionParam==null){
                return null;
            }
            try {
                String s = objectMapper.writeValueAsString(userAttentionParam);
                HashMap hashMap = objectMapper.readValue(s, HashMap.class);
                //先放入redis
                redisUtils.hmset(Constant.Project.REDIS_PROJECT_ATTENTION_KEY+userAttentionParam.getProjectId(),hashMap);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return  userAttentionParam;
        }
        try {
            String s = objectMapper.writeValueAsString(hmget);
            UserAttentionParam userAttentionParam = objectMapper.readValue(s, UserAttentionParam.class);
            return userAttentionParam;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public void cancelAttention(String projectId, String userId) {
        dmsjProjectAttentionMapper.deleteByPrimaryKey(DmsjProjectAttentionKey.builder().projectId(projectId).userId(userId).build());
        redisUtils.lrem(Constant.DmsjUser.REDIS_USER_ATTENTION_PROJECT_IDS + userId,0,projectId);
        //取消关注 当前项目的关注数应该-1
        redisUtils.hincr(Constant.Project.REDIS_PROJECT_ATTENTION_KEY + projectId,Constant.UserAttentionParam.REDIS_HASH_ATTENTION,-1);

    }
}
