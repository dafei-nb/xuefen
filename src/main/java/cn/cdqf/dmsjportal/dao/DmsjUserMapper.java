package cn.cdqf.dmsjportal.dao;

import cn.cdqf.dmsjportal.entity.DmsjUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface DmsjUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DmsjUser record);

    int insertSelective(DmsjUser record);

    DmsjUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DmsjUser record);

    int updateByPrimaryKey(DmsjUser record);

    DmsjUser login(@Param("username") String username);

    int usernamecheck(@Param("username")String username);

    int phonecheck(@Param("phone")String phone);

    int idcardcheck(@Param("idCard")String idCard);

    DmsjUser loginByPhone(@Param("phone") String phone);

    DmsjUser getUserByUserId(@Param("userid") String userid);
}