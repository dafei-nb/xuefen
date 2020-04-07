package cn.cdqf.dmsjportal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAsync
@EnableSwagger2
//扫描mapper所在的包
@MapperScan("cn.cdqf.dmsjportal.dao")
//@EnableScheduling
public class DmsjPortalApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DmsjPortalApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DmsjPortalApplication.class);
    }
}
