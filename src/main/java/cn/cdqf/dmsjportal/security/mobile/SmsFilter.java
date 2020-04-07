package cn.cdqf.dmsjportal.security.mobile;

import cn.cdqf.dmsjportal.common.Constant;
import cn.cdqf.dmsjportal.security.DmsjAuthenticationFailureHandler;
import cn.cdqf.dmsjportal.security.imagecode.ImageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@Component
public class SmsFilter extends OncePerRequestFilter {
    @Autowired
    private DmsjAuthenticationFailureHandler dmsjAuthenticationFailureHandler;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (Constant.DmsjUser.LOGIN_URL_MOBILE.equals(httpServletRequest.getRequestURI()) && "POST".equals(httpServletRequest.getMethod())){
            try{
                validate(httpServletRequest);
            }catch (AuthenticationException e){
                //失败处理
                dmsjAuthenticationFailureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
                return;
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    public void validate(HttpServletRequest httpServletRequest) {
        //获取前台的验证码
        String smsCode = httpServletRequest.getParameter("smsCode");
        if (smsCode == null || smsCode.equals("")) {
            throw new InternalAuthenticationServiceException("输入的验证码不能为空");
        }
        //获取session中存储的验证码
        HttpSession session = httpServletRequest.getSession();
        String attribute =(String)session.getAttribute(Constant.Security.DMSJ_PHONE_CODE);
        if (attribute==null){
            throw  new InternalAuthenticationServiceException("验证码不存在");
        }
        //说明验证成功 删除session
        httpServletRequest.getSession().removeAttribute(Constant.Security.DMSJ_PHONE_CODE);

    }

}
