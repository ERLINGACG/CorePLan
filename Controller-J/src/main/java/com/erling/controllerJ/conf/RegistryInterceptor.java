package com.erling.controllerJ.conf;

import com.erling.controllerJ.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Lazy // 延迟加载拦截器，避免循环依赖问题
public class RegistryInterceptor implements WebMvcConfigurer { // 拦截器注册器

    private final AuthInterceptor authInterceptor;
    @Autowired
    public RegistryInterceptor(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }
    @Override
    public void addInterceptors(@Lazy InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/test/interceptor")

        ; // 拦截路径
//                .excludePathPatterns("/user/login", "/user/register","/user/get-captcha","/user/verify-captcha");
    }
}
