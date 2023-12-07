package com.example.chamedu_v1.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {

    private static final Logger log = LoggerFactory.getLogger(WebsocketConfig.class);
    private final WebsocketHandler websocketHandler;

    // Handshaking
    @Override
    public void registerWebSocketHandlers( @NonNull WebSocketHandlerRegistry registry) {
        try {
            log.info("WebSocket handlers 를 등록 시도..");
            registry.addHandler(websocketHandler, "api/chat/{roomNumber}")
                    .setAllowedOrigins("*"); // 클라이언트의 요청을 허용
            log.info("WebSocket handlers 등록 성공!");
        } catch (Exception e) {
            log.error("WebSocket handler 등록 중 에러가 발생했습니다 : ", e);
        }

    }
}

// 클라이언트가 ws://localhost:8080/ws/chat{roomId}으로 Websocket연결 열기