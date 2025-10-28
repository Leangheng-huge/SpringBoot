package com.chiikawa.demo.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
@ConfigurationProperties(prefix = "config")
@Data
public class ApplicationConfiguration {
    private Security security;
    private Pagination pagination;
    private JsonPlaceholder jsonPlaceholder;
    private Telegram telegram;

   @Data
    public static class Security {
        private String secret;
        private long expiration;
        private long refreshTokenExpiration; // refresh-token-expiration
    }

    @Data
    public static class Pagination {
        private String baseUrl;
        private HashMap<String,String> uri;

        public String getUrlByResource(String resource) {
            return baseUrl.concat(uri.getOrDefault(resource,""));
        }
    }

   @Data
    public static class JsonPlaceholder {
        private String baseUrl;
        private HashMap<String,String> uri;

        public String getPostsUri() {
            return uri.get("posts");
        }

        public String getCommentsUri() {
            return uri.get("comments");
        }
    }

    @Data
    public static class Telegram {
        private String baseUrl;
        private String token;
        private String chatId;

        public String getBaseUrl() {
            return this.baseUrl.replace("{TOKEN}", this.token);
        }
    }
}