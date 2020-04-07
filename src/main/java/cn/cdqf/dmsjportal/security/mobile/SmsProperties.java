package cn.cdqf.dmsjportal.security.mobile;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("code.sms")
@Configuration
@Data
public class SmsProperties {
    private String APIURL;
    private String APPID;
    private String APPSECRET;
    private String INIT_WORD;
    private int length;
    private int expire;
}
