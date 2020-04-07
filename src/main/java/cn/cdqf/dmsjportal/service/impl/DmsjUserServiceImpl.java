package cn.cdqf.dmsjportal.service.impl;

import cn.cdqf.dmsjportal.common.ResultEnum;
import cn.cdqf.dmsjportal.common.ResultResponse;
import cn.cdqf.dmsjportal.dao.DmsjUserMapper;
import cn.cdqf.dmsjportal.dao.DmsjWxMapper;
import cn.cdqf.dmsjportal.entity.DmsjUser;
import cn.cdqf.dmsjportal.entity.DmsjWx;
import cn.cdqf.dmsjportal.entity.param.DmsjUserParam;
import cn.cdqf.dmsjportal.service.DmsjUserService;
import cn.cdqf.dmsjportal.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class DmsjUserServiceImpl implements DmsjUserService {
    @Autowired
    private DmsjUserMapper dmsjUserMapper;
    @Autowired
    private DmsjWxMapper dmsjWxMapper;
    private PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();


    @Override
    public DmsjUser login(String username) {
        return dmsjUserMapper.login(username);
    }

    @Override
    public DmsjUser loginByPhone(String phone) {
        return dmsjUserMapper.loginByPhone(phone);
    }

    @Override
    public ResultResponse register(DmsjUserParam dmsjUserParam) {
        //唯一校验 用户名 电话 身份证
        ResultResponse check=check(dmsjUserParam);
        if (!check.isSuccess()){
            return check;
        }
        DmsjUser dmsjUser = DmsjUser.builder().address(dmsjUserParam.getAddress())
                .idCard(dmsjUserParam.getIdCard())
                .password(passwordEncoder.encode(dmsjUserParam.getPassword()))
                .username(dmsjUserParam.getUsername())
                .phone(dmsjUserParam.getPhone()).build();
        dmsjUser.setUserId(UUID.randomUUID().toString().replace("-","").substring(0,15));
        //ip地址
        dmsjUser.setRegisterIp((String) ThreadLocalUtil.get());
        dmsjUserMapper.insertSelective(dmsjUser);
        return ResultResponse.success();

    }

    @Override
    public DmsjWx selectByOpenId(String openid) {
        return dmsjWxMapper.selectByOpenId(openid);
    }

    @Override
    public DmsjUser getUserByUserId(String userid) {
        return dmsjUserMapper.getUserByUserId(userid);
    }

    @Override
    public void insertWx(DmsjWx dmsjWx) {
        dmsjWxMapper.insertWx(dmsjWx);
    }


    private ResultResponse check(DmsjUserParam dmsjUserParam){
        if (dmsjUserMapper.usernamecheck(dmsjUserParam.getUsername())>0){
            return ResultResponse.fail(ResultEnum.USERNAME_REPEAT);
        }
        if (dmsjUserMapper.phonecheck(dmsjUserParam.getPhone())>0){
            return ResultResponse.fail(ResultEnum.USERNAME_REPEAT);
        }
        if (dmsjUserMapper.idcardcheck(dmsjUserParam.getIdCard())>0){
            return ResultResponse.fail(ResultEnum.USERNAME_REPEAT);
        }
        return ResultResponse.success();
    }

}
