package com.sd.lifeng.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author bmr
 * @Classname JwtConfig
 * @Description
 * @Date 2020/5/20 8:55:51
 * @Created by bmr
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    private String key;
    private int expire;
    private String unit;
}
