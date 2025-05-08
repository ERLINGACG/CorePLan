package com.erling.conf;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig {
    @Bean
    public DefaultKaptcha kaptcha() {
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Properties props = new Properties();
        // 配置Kaptcha的属性
        props.put("kaptcha.border", "no");                 // 无边框
        props.put("kaptcha.textproducer.font.color", "black"); // 字体颜色
        props.put("kaptcha.textproducer.char.space", "4");  // 字符间距
        props.put("kaptcha.image.width", "160");           // 图片宽度
        props.put("kaptcha.image.height", "50");           // 图片高度
        props.put("kaptcha.textproducer.char.length", "4"); // 字符数量
        Config config = new Config(props);
        kaptcha.setConfig(config);
        return kaptcha;
    }
}
