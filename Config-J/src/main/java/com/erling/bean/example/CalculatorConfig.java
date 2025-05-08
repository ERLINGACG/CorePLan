package com.erling.bean.example;

import com.erling.controllerJ.example.GetCalculatorData;
import com.erling.controllerJ.example.LoadLibTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CalculatorConfig implements WebMvcConfigurer {


    @Bean
    public GetCalculatorData getCalculatorData() {
        return new GetCalculatorData();
    }
    @Bean
    public LoadLibTest loadLibTest() {
        return new LoadLibTest();
    }
}
