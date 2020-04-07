package cn.cdqf.dmsjportal.service;

import cn.cdqf.dmsjportal.common.ResultResponse;

public interface ShowService {
    ResultResponse showAll(Integer type,Integer status,Integer order);
}
