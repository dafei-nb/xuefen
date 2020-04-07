package cn.cdqf.dmsjportal.security.imagecode;


import cn.cdqf.dmsjportal.common.Constant;
import cn.cdqf.dmsjportal.security.DmsjAuthenticationFailureHandler;
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
public class ImageCodeFilter extends OncePerRequestFilter {
    @Autowired
    private DmsjAuthenticationFailureHandler dmsjAuthenticationFailureHandler;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
            if (Constant.DmsjUser.LOGIN_URL.equals(httpServletRequest.getRequestURI()) && "POST".equals(httpServletRequest.getMethod())){
                //判断验证码
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

    //取得前台输入框的验证码，待会用来比较
    public void validate(HttpServletRequest httpServletRequest) {
        //获取前台的验证码
        String imageCode = httpServletRequest.getParameter("imageCode");
        if (imageCode == null || imageCode.equals("")) {
            throw new InternalAuthenticationServiceException("输入的验证码不能为空");
        }
        //获取session中存储的验证码
        HttpSession session = httpServletRequest.getSession();
        ImageCode attribute = (ImageCode) session.getAttribute(Constant.Security.IMAGE_CODE_KEY);
        if (attribute==null){
            throw  new InternalAuthenticationServiceException("验证码不存在");
        }
        if (attribute.isExpire()){
            throw new InternalAuthenticationServiceException("验证码过期");
        }
        if(!imageCode.equals(attribute.getCode())){
            throw  new InternalAuthenticationServiceException("验证码不匹配，请输入正确的验证码");
        }
        //说明验证成功 删除session
        httpServletRequest.getSession().removeAttribute(Constant.Security.IMAGE_CODE_KEY);

    }

}
