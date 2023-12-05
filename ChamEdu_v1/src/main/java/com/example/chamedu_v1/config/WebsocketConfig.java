package com.example.chamedu_v1.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {

    private final WebsocketHandler websocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(websocketHandler, "/chat/{roomNumber}");
    }
}

// 클라이언트가 ws://localhost:8080/chat으로 커넥션을 연결하고 메시지 통신