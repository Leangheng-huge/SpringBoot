package com.chiikawa.demo.common.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "config")
@Configuration
public class ApplicationConfiguration {
    private Security security;

    @Getter
    public static class Security {
        private String secret ;
        private long expirationTime ;
        private long refreshExpirationTime ;
    }

}
