package cn.cdqf.dmsjportal.security;

import cn.cdqf.dmsjportal.properties.GlobleProperties;

import cn.cdqf.dmsjportal.security.imagecode.ImageCodeFilter;
import cn.cdqf.dmsjportal.security.mobile.SmsAuthenticationFilter;
import cn.cdqf.dmsjportal.security.mobile.SmsAuthenticationProvider;
import cn.cdqf.dmsjportal.security.mobile.SmsFilter;
import cn.cdqf.dmsjportal.security.weixin.WxAuthenticationFilter;
import cn.cdqf.dmsjportal.security.weixin.WxAuthenticationProvider;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private GlobleProperties globleProperties;
    @Autowired
    private DmsjAuthenticationSuccessHandler dmsjAuthenticationSuccessHandler;
@Autowired
private SmsFilter smsFilter;
    @Autowired
    private ImageCodeFilter imageCodeFilter;
    @Autowired
    private DmsjAuthenticationFailureHandler dmsjAuthenticationFailureHandler;
    @Autowired
    private SecurityServiceImpl securityService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        cn.cdqf.dmsjportal.security.mobile.SmsAuthenticationFilter smsAuthenticationFilter = new SmsAuthenticationFilter();
        //需要设置security自带AuthenticationManager 这里面有List<Provider>
        //设置成功失败处理
        smsAuthenticationFilter.setAuthenticationSuccessHandler(dmsjAuthenticationSuccessHandler);
        smsAuthenticationFilter.setAuthenticationFailureHandler(dmsjAuthenticationFailureHandler);
        cn.cdqf.dmsjportal.security.mobile.SmsAuthenticationProvider smsAuthenticationProvider = new SmsAuthenticationProvider();
        smsAuthenticationProvider.setUserDetailsService(securityService);
        smsAuthenticationFilter.setAuthenticationManager(new ProviderManager(Lists.newArrayList(smsAuthenticationProvider)));

        cn.cdqf.dmsjportal.security.weixin.WxAuthenticationFilter wxAuthenticationFilter = new WxAuthenticationFilter();
        //需要设置security自带AuthenticationManager 这里面有List<Provider>
        //设置成功失败处理
        wxAuthenticationFilter.setAuthenticationSuccessHandler(dmsjAuthenticationSuccessHandler);
        wxAuthenticationFilter.setAuthenticationFailureHandler(dmsjAuthenticationFailureHandler);
        cn.cdqf.dmsjportal.security.weixin.WxAuthenticationProvider wxAuthenticationProvider = new WxAuthenticationProvider();
        wxAuthenticationProvider.setUserDetailsService(securityService);
        wxAuthenticationFilter.setAuthenticationManager(new ProviderManager(Lists.newArrayList(wxAuthenticationProvider)));

        //表单形式登录  设置登录页面
        //防止自定义页面被定为攻击
        http
                //.addFilterBefore(wxFilter,UsernamePasswordAuthenticationFilter.class)
                //.addFilterAfter(wxAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                //.authenticationProvider(wxAuthenticationProvider)
                //把自定义的filter与provider交给springsecurity管理
                .authenticationProvider(smsAuthenticationProvider)
                .addFilterBefore(smsFilter,UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(smsAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                //图片验证码过滤器加到表单验证之前
                .addFilterBefore(imageCodeFilter, UsernamePasswordAuthenticationFilter.class)
                //配置验证码过滤器在表单过滤器之前
                .formLogin()
                //访问项目 如果没有认证  就会自动跳转到配置的登录页面
                .loginPage("/authentication/login").loginProcessingUrl("/aaa/login").permitAll()
                //定义自己的登录成功后执行的逻辑
                .successHandler(dmsjAuthenticationSuccessHandler)
                .failureHandler(dmsjAuthenticationFailureHandler)
                //.loginProcessingUrl("/login")
                //.httpBasic()
                .and()
                //所有的请求
                .authorizeRequests()
                //不拦截 login.html
                .antMatchers("/authentication/login",globleProperties.getLoginHtml(),"/code/**","/wx/**").permitAll()
                .anyRequest()
                //要被拦截
                .authenticated()

        ;
    }

    //放过静态资源

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**","/js/**","/img/**","/bootstrap-3.3.7-dist/**");
    }

    //配置密码加密
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
