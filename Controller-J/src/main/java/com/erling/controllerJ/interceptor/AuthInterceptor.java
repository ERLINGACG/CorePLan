package com.erling.controllerJ.interceptor;

import com.erling.utilJ.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(
            HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull Object handler

    ) throws IOException {
        log.info("拦截器触发 - 请求路径: {} | 方法: {}",
                request.getRequestURI(),
                request.getMethod());

        String path = request.getRequestURI();
        String userAgent = request.getHeader("User-Agent");
        if (isSpider(userAgent)) {
            log.warn("拦截爬虫访问: {} | UA: {}", path, userAgent);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Automated traffic not allowed");
            return false;
        }

        String token = request.getHeader("Authorization");
        System.out.println(token);
        if (token == null) {
            log.warn("拦截无token访问: {} | UA: {}", path, userAgent);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token not found");
            return false;
        }

        String username = JwtUtils.extractUsername(token);
        if(JwtUtils.validateToken(token,username)){
            response.setStatus(HttpServletResponse.SC_OK);
            System.out.println("用户名："+username);
            return true;
        }



        return true;
    }



    private boolean isSpider(String userAgent) {
        if (userAgent == null) return true; // 无UA视为可疑
        String[] spiderKeywords = {"bot", "spider", "crawler", "slurp", "yahoo"};
        String lowerUA = userAgent.toLowerCase();
        for (String keyword : spiderKeywords) {
            if (lowerUA.contains(keyword)) {
                return true;
            }
        }
        return false;
    }
}
