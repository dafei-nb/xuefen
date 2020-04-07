package cn.cdqf.dmsjportal.dao;

import cn.cdqf.dmsjportal.entity.DmsjProjectAttentionKey;
import cn.cdqf.dmsjportal.entity.param.UserAttentionParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DmsjProjectAttentionMapper {
    int deleteByPrimaryKey(DmsjProjectAttentionKey key);

    int insert(DmsjProjectAttentionKey record);

    int insertSelective(DmsjProjectAttentionKey record);

    List<String> queryProjectIdsByUserId(@Param("userId") String userId);

    UserAttentionParam queryAttentionParamById(@Param("projectId") String projectId);
}