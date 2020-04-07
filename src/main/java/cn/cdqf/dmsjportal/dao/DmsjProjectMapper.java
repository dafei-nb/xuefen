package cn.cdqf.dmsjportal.dao;

import cn.cdqf.dmsjportal.entity.DmsjProject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DmsjProjectMapper {
    int deleteByPrimaryKey(String projectId);

    int insert(DmsjProject record);

    int insertSelective(DmsjProject record);

    DmsjProject selectByPrimaryKey(String projectId);

    int updateByPrimaryKeySelective(DmsjProject record);

    int updateByPrimaryKey(DmsjProject record);

    List<DmsjProject> queryByDeafult();

    List<String> projectExpire();

    void updateStatuByIds(@Param("ids") List<String> ids, @Param("status") int i);
}