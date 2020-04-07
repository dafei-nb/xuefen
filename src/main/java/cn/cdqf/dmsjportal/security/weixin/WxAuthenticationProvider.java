package cn.cdqf.dmsjportal.security.weixin;

import cn.cdqf.dmsjportal.security.SecurityServiceImpl;
import cn.cdqf.dmsjportal.security.mobile.SmsAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class WxAuthenticationProvider implements AuthenticationProvider {
    private SecurityServiceImpl userDetailsService;
    public void setUserDetailsService(SecurityServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //authentication就是filter传递过来的token  this.getAuthenticationManager().authenticate(authRequest)
        WxAuthenticationToken wxAuthenticationToken = (WxAuthenticationToken)authentication;
        if (wxAuthenticationToken.getCredentials()!=null){
            UserDetails userDetails = userDetailsService.loadUserByWx(wxAuthenticationToken.getCredentials());
        }
        UserDetails userDetails = userDetailsService.loadUserByWx(wxAuthenticationToken.getPrincipal());

        //根据电话号码没有查到对应的信息
        if(userDetails==null){
            throw new InternalAuthenticationServiceException("用户未注册");
        }
        //说明校验成功了 这个构造方法中 有一行代码设置认证成功
        WxAuthenticationToken success = new WxAuthenticationToken(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
        //把刚才的请求信息设置进新的success
        success.setDetails(wxAuthenticationToken.getDetails());
        return success;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        //AuthenticationManager 管理List<Provider>  遍历这个集合 然后根据token类型选择对应provider
        //isAssignableFrom:前面类型与后面类型相同或者是后面类型的父类型
        return WxAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
