package com.erling.conf.websocket;

import com.erling.controllerJ.example.VideoStreamWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket // 修改注解为启用普通 WebSocket（非 STOMP）
public class VideoWebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 添加视频流处理器
        registry.addHandler(videoStreamHandler(), "/video-stream")
                .setAllowedOrigins("*");
    }
    @Bean
    public WebSocketHandler videoStreamHandler() {
        return new VideoStreamWebSocketHandler();
    }
}
