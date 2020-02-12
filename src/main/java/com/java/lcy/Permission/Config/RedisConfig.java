package com.java.lcy.Permission.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix="spring.redis")
public class RedisConfig {
    private String host;
    private int port;
    private String password;
    private int timeout;
    private int database;
    private int maxIdle;
    private int maxActive;
    private int minIdle;
}
