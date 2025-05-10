package com.erling.aiJ.deepseek.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
public class ChatbotController {

    private final ChatClient chatClient;

    public ChatbotController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping(value = "/ai/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> streamChat() {
        return chatClient.prompt("你好，请你介绍自己")
                .stream()  // 返回 StreamResponseSpec
                .content()  // 获取 Flux<String> 内容流
                .map(content -> ServerSentEvent.builder(content)
                        .event("message")
                        .build())
                .onErrorResume(e -> Flux.just(
                        ServerSentEvent.builder("服务异常: " + e.getMessage())
                                .event("error")
                                .build()
                ));
    }
}
