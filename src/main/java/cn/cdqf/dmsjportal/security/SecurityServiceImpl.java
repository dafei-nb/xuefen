package cn.cdqf.dmsjportal.security;

import cn.cdqf.dmsjportal.entity.DmsjUser;
import cn.cdqf.dmsjportal.service.DmsjUserService;
import cn.cdqf.dmsjportal.util.ThreadLocalUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
@Slf4j
public class SecurityServiceImpl implements UserDetailsService {
    @Autowired
    private DmsjUserService dmsjUserService;
    private PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //s就是username  根据用户名或者电话查询去数据库查询
        //第二个参数 传从数据库查询到的密码  第三个参数是角色
        DmsjUser login = dmsjUserService.login(s);
        String password = login.getPassword();
        String encode = passwordEncoder.encode(password);
        log.info("加密后的结果:{}",encode);
        return new User(s,encode,
                Lists.newArrayList(new SimpleGrantedAuthority("manager")));
    }

    public UserDetails loadUserByPhone(String s) throws UsernameNotFoundException {
        //s就是username  根据用户名或者电话查询去数据库查询
        //第二个参数 传从数据库查询到的密码  第三个参数是角色
        DmsjUser loginByPhone = dmsjUserService.loginByPhone(s);
        ThreadLocalUtil.setDmsjUser(loginByPhone);
        if(loginByPhone==null){
            throw  new InternalAuthenticationServiceException("当前电话未注册");
        }
        String username = loginByPhone.getUsername();
        String password = loginByPhone.getPassword();
        String encode = passwordEncoder.encode(password);

        return new User(username,encode,
                Lists.newArrayList(new SimpleGrantedAuthority("manager")));
    }
    public UserDetails loadUserByWx(String s) throws UsernameNotFoundException {
        //s就是username  根据用户名或者电话查询去数据库查询
        //第二个参数 传从数据库查询到的密码  第三个参数是角色
       /* DmsjUser loginByPhone = dmsjUserService.loginByPhone(s);
        if(loginByPhone==null){
            throw  new InternalAuthenticationServiceException("当前电话未注册");
        }
        String username = loginByPhone.getUsername();
        String password = loginByPhone.getPassword();*/
        String encode = passwordEncoder.encode("123");

        return new User(s,encode,
                Lists.newArrayList(new SimpleGrantedAuthority("manager")));
    }

}
