package com.example.chamedu_v1.config;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Log4j2
public class WebsocketHandler extends TextWebSocketHandler{

    private static List<WebSocketSession> list = new ArrayList<>();


    // 사용자의 메시지를 받으면 동작. CLIENTS 객체에 담긴 세션 값들을 가져와서 반복문을 통해 메시지를 발송
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload : " + payload);

        for(WebSocketSession sess: list) {
            sess.sendMessage(message);
        }
    }


    // 사용자가 웹소켓 서버에 접속 시 CLIENTS에 담음
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        list.add(session);
        log.info(session + " 클라이언트 접속");
    }

    // 사용자 접속 끊길 시 호출. CLIENTS 객체에서 접속이 끊어진 아이디 값을 제거
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info(session + " 클라이언트 접속 해제");
        list.remove(session);
    }

}
