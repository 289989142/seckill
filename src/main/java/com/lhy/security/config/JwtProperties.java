package com.lhy.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName JwtProperties
 * @Description jwt配置
 * @Author lihengyu
 * @Date 2020/12/6 15:33
 * @Version 1.0
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "config.jwt")
public class JwtProperties {

    /**
     *密钥
     */
    public  String secret;
    /**
     * 过期时间
     */
    public int expire;
    /**
     * 名称
     */
    public String header;


}