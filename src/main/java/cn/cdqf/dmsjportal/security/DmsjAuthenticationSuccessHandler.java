package cn.cdqf.dmsjportal.security;

import cn.cdqf.dmsjportal.common.Constant;
import cn.cdqf.dmsjportal.common.ResultResponse;
import cn.cdqf.dmsjportal.properties.GlobleProperties;
import cn.cdqf.dmsjportal.util.ThreadLocalUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class DmsjAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private GlobleProperties globleProperties;
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        if(Constant.Globle.LOGIN_SUCCESS_JSON_STYLE.equals(globleProperties.getFinshLoginStyle())){
            response.setContentType("application/json;charset=utf-8");
            String authenticationStr = objectMapper.writeValueAsString(ResultResponse.success(authentication.getPrincipal()));

            request.getSession().setAttribute(Constant.DmsjUser.LOGIN_USER_KEY, ThreadLocalUtil.getDmsjUser());
            ThreadLocalUtil.removeDmsjUser();
            //认证成功以后security把用户登录信息放在session中
            //为了方便在项目中任意地方可以取 SecurityContextHolder：就是封装的ThreadLocal
            SecurityContext context = SecurityContextHolder.getContext();
            response.getWriter().write(authenticationStr);
        }else{
            //http:8080/aaaa
            HttpSessionRequestCache httpSessionRequestCache = new HttpSessionRequestCache();
            SavedRequest savedRequest = httpSessionRequestCache.getRequest(request, response);
            //有缓存跳转地址
            if(savedRequest!=null){
                RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
                redirectStrategy.sendRedirect(request,response,savedRequest.getRedirectUrl());
            }else{
                super.onAuthenticationSuccess(request,response,authentication);
            }
        }

    }
}
