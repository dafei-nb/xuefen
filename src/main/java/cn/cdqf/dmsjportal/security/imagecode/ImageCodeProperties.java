package cn.cdqf.dmsjportal.security.imagecode;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("code.image")
@Data
public class ImageCodeProperties {

    private int length;
    private int expire;
    private int width;
    private int height;
}
