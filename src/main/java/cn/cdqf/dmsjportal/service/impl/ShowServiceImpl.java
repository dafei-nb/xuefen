package cn.cdqf.dmsjportal.service.impl;

import cn.cdqf.dmsjportal.common.ResultResponse;
import cn.cdqf.dmsjportal.dao.DmsjProjectMapper;
import cn.cdqf.dmsjportal.entity.DmsjProject;
import cn.cdqf.dmsjportal.service.ShowService;
import cn.cdqf.dmsjportal.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowServiceImpl implements ShowService {
    @Autowired
    @SuppressWarnings("all")
    private DmsjProjectMapper dmsjProjectMapper;
    @Autowired
    private RedisUtils redisUtils;
    @Override
    public ResultResponse showAll(Integer type, Integer status, Integer order) {
        if (type==null && status==null && order==null){
            //先从redis中查
            /*redis代码*/
            List<DmsjProject> dmsjProjects = dmsjProjectMapper.queryByDeafult();
            return ResultResponse.success(dmsjProjects);

        }
       /* if (status==null){

        }
        if (type==null){
        }
        if (order==null){

        }*/

        return null;
    }
}
