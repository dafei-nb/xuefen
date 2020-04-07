package cn.cdqf.dmsjportal.service;

import cn.cdqf.dmsjportal.common.ResultResponse;
import cn.cdqf.dmsjportal.entity.DmsjUser;
import cn.cdqf.dmsjportal.entity.DmsjWx;
import cn.cdqf.dmsjportal.entity.param.DmsjUserParam;


public interface DmsjUserService {
   DmsjUser login(String username);
    DmsjUser loginByPhone(String phone);

    ResultResponse register(DmsjUserParam dmsjUserParam);

    DmsjWx selectByOpenId(String openid);

    DmsjUser getUserByUserId(String userid);

     void insertWx(DmsjWx dmsjWx);
}
