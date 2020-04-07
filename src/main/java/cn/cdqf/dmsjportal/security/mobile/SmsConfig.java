package cn.cdqf.dmsjportal.security.mobile;

import cn.cdqf.dmsjportal.security.DmsjAuthenticationFailureHandler;
import cn.cdqf.dmsjportal.security.DmsjAuthenticationSuccessHandler;
import cn.cdqf.dmsjportal.security.SecurityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


//@Configuration
public class SmsConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DmsjAuthenticationFailureHandler dmsjAuthenticationFailureHandler;
    @Autowired
    private DmsjAuthenticationSuccessHandler dmsjAuthenticationSuccessHandler;
    @Autowired
    private SecurityServiceImpl securityService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {


    }
}
