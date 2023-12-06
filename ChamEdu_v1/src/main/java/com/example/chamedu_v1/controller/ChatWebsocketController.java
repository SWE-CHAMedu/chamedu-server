package com.example.chamedu_v1.controller;

import com.example.chamedu_v1.service.ChatManageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
@Log4j2
public class ChatWebsocketController {

    @Autowired
    private ChatManageService chatManageService;

    @GetMapping("/chat/enter/{roomId}") // 버튼 누를 때
    public ResponseEntity<String> startChat(@PathVariable Integer roomId) {

        if (chatManageService.checkChatNow(roomId)) { // 현재가 채팅이 가능한 시간인지 확인되면
            // 여기서 WebSocket 연결을 프론트에서 해야함.
            return ResponseEntity.ok("채팅방으로 입장하는 중... ");
        } else {
            log.info("예약된 상담시간이 아닙니다.");
            return ResponseEntity.ok("예약된 상담시간이 아닙니다.");
        }
    }


    /*
    @GetMapping("/ws/chat")
    public ResponseEntity<String> handleWebSocketRequest(
            @RequestHeader("Sec-WebSocket-Version") String webSocketVersion,
            @RequestHeader("Sec-WebSocket-Key") String webSocketKey,
            @RequestHeader("Connection") String connection,
            @RequestHeader("Upgrade") String upgrade,
            @RequestHeader("Sec-WebSocket-Extensions") String webSocketExtensions,
            @RequestHeader("Host") String host) {

        // 여기서 받아온 헤더 값을 사용하거나 처리할 작업을 수행할 수 있습니다.
        // 예를 들어, WebSocket 연결 처리 등...
        log.info("@ChatWebsocketController, chat GET()");

        return ResponseEntity.ok("WebSocket 요청을 처리했습니다.");
    }*/

    /*@GetMapping("/chat")
    public String chatGET(){
        log.info("@ChatWebsocketController, chat GET()");
        return "chat";
    }
    */

}