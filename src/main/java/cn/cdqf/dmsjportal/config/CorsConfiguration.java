package cn.cdqf.dmsjportal.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.thetransactioncompany.cors.CORSFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cors")
public class CorsConfiguration {
    private String legalClients ;
    public void setLegalClients(String legalClients) {
        this.legalClients = legalClients;
    }

    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.addInitParameter("cors.allowSubdomains","true"); // 是否开启二级域名跨域
        //这儿写*为了方便  www.baidu.com,www.4377.com
        registration.addInitParameter("cors.allowOrigin","*");// 放行的域名list 以"," 号分割
        registration.addUrlPatterns("/*");
        CORSFilter corsFilter= new CORSFilter();
        registration.setName("CORSFilter");
        registration.setFilter(corsFilter);
        return registration;
    }
}