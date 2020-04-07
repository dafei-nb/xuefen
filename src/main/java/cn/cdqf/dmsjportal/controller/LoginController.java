package cn.cdqf.dmsjportal.controller;

import cn.cdqf.dmsjportal.common.Constant;
import cn.cdqf.dmsjportal.common.ResultEnum;
import cn.cdqf.dmsjportal.common.ResultResponse;
import cn.cdqf.dmsjportal.properties.GlobleProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@Slf4j
public class LoginController {
    RequestCache requestCache=new HttpSessionRequestCache();

    RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();
@Autowired
private GlobleProperties globleProperties;
//在这个方法中，根据配置判断到底是哪种方式
    @GetMapping("authentication/login")
    public ResultResponse login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
            log.info("被springsecurity拦截的请求: {}",savedRequest);
        //如果是页面请求,跳转登录页面
        if (Constant.Globle.HTML_STYLE.equals(globleProperties.getStyle())){
            String loginHtml = globleProperties.getLoginHtml();
            //重定向
            redirectStrategy.sendRedirect(request,response,loginHtml);
        }
        //如果是ajax请求，返回json
        return ResultResponse.fail(ResultEnum.NO_LOGIN);

    }

}
