package com.missfresh.sys.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <pre>
 *
 * </pre>
 * <small> 2018/8/23 16:36 | caigl@missfresh.cn</small>
 */
@Component
@ConfigurationProperties(prefix = "ifast.swagger")
@Data
public class SwaggerProperties {
    private String title;
    private String contactName;
    private String contactUrl;
    private String contactEmail;
    private String version;
    private String description;

}
