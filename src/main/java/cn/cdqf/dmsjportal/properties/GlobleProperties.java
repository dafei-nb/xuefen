package cn.cdqf.dmsjportal.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "dmsj")
@Data
public class GlobleProperties {
    private String style;
    private String loginHtml="/index.html";

    private String finshLoginStyle="html";

    private String errorMailSubject;
}
