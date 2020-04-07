package cn.cdqf.dmsjportal.dao;

import cn.cdqf.dmsjportal.entity.DmsjWx;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface DmsjWxMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DmsjWx record);

    int insertSelective(DmsjWx record);

    DmsjWx selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DmsjWx record);

    int updateByPrimaryKey(DmsjWx record);

    DmsjWx selectByOpenId(@Param("openid") String openid);

    void insertWx(DmsjWx dmsjWx);

}