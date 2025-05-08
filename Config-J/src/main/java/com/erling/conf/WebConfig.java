package com.erling.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings (CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")        // 前端地址
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // 请求方法
                .allowedHeaders("*")                           // 请求头
                .allowCredentials(false)                       // 是否允许凭证
                .maxAge(3600);                                  // 预检请求的有效期
    }
}
