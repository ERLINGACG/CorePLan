package com.erling.conf.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig   {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 使用新的 Lambda 风格配置
                .csrf(AbstractHttpConfigurer::disable)                                      // 禁用 CSRF 保护
                .sessionManagement(session -> session // 配置会话管理
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)            // 无状态会话
                )
                .authorizeHttpRequests(auth -> auth          // 配置请求授权
                        .anyRequest().permitAll()                                         // 允许所有请求

                );

        return http.build(); // 返回配置好的 SecurityFilterChain
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web
                .ignoring()
                .requestMatchers(
                        new AntPathRequestMatcher("/ignore1"), // 忽略的路径
                        new AntPathRequestMatcher("/ignore2")  // 忽略的路径
                );
    }
}
