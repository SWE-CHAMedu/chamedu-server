package com.example.chamedu_v1.controller;

import com.example.chamedu_v1.data.dto.ChatCompleteDto;
import com.example.chamedu_v1.service.ChatManageService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Log4j2
public class ChatWebsocketController {

    @Autowired
    private ChatManageService chatManageService;

   /* @GetMapping("/chat/enter/{roomId}") // 버튼 누를 때
    public ResponseEntity<String> startChat(@PathVariable Integer roomId) {
        if (chatManageService.checkChatNow (roomId)) { // 현재가 채팅이 가능한 시간인지 확인되면
            if (chatManageService.checkPointMentee(roomId)) {
                // 여기서 WebSocket 연결을 프론트에서 해야함.
                log.info("웹소켓으로 전환해줍니다.");
                return ResponseEntity.ok("채팅방으로 입장하는 중... ");
            } else{
                return ResponseEntity.ok("잔액부족. 참포인트 충전이 필요해요"); // 참포인트 충전페이지
            }
        } else {
            log.info("예약된 상담시간이 아닙니다.");
            return ResponseEntity.ok("예약된 상담시간이 아닙니다.");
        }
    }*/

    /*@RequestMapping(value = "/chat/enter/{roomId}", method = RequestMethod.GET)
    public ResponseEntity<String> handleWebSocketUpgrade() {
        HttpHeaders headers = new HttpHeaders();

        // HTTP 응답 헤더에 웹소켓으로의 업그레이드를 표시
        headers.add(HttpHeaders.UPGRADE, "websocket");
        headers.add(HttpHeaders.CONNECTION, "Upgrade");

        // 클라이언트에게 101 Switching Protocols로 응답
        return new ResponseEntity<>(headers, HttpStatus.SWITCHING_PROTOCOLS);
    }*/



//    @GetMapping("/chat/{roomId}")
//    public String chatGET(@PathVariable Integer roomId, Model model){
//
//        log.info("@ChatWebsocketController, chat GET()");
//
//        return "chat/{roomId}"; // 렌더링할 뷰 이름
//    }


}