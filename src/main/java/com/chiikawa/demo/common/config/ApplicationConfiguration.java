package com.chiikawa.demo.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Data
@ConfigurationProperties(prefix = "config")
@Configuration
public class ApplicationConfiguration {
    private Security security;
    private Pagination pagination;
    private JsonPlaceholder jsonPlaceholder;

   @Data
    public static class Security {
        private String secret ;
        private long expirationTime ;
        private long refreshTokenExpiration ;

    }

    @Data
    public static class Pagination {
       private String baseUrl;
      private HashMap<String, String> uri;

      public String getUrlByResource(String resource) {
          return baseUrl.concat(uri.getOrDefault(resource,""));
      }
    }

    @Data
    public static class JsonPlaceholder{
       private String baseUrl;
       private HashMap<String, String> uri;

       public String getPostUrl(){
           return uri.get("posts");
        }

        public String getCommentsUri(){
           return uri.get("comments");
        }
    }
}
