package com.example.chamedu_v1.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Log4j2
public class WebsocketHandler extends TextWebSocketHandler{

    public static final String ROOM_NUMBER = "roomNumber";
    private static final Logger logger = LoggerFactory.getLogger(WebsocketHandler.class);

    List<HashMap<String,Object>> list = new ArrayList<>(); // 세션리스트

    // 사용자의 메시지를 받으면 동작 - CLIENTS 객체에 담긴 세션 값들을 가져와서 반복문을 통해 메시지를 발송
    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, @NotNull TextMessage message) throws Exception {

        String msg = message.getPayload();
        JSONObject obj = jsonToObjectParser(msg);

        String rN = (String) obj.get(ROOM_NUMBER);
        HashMap<String, Object> temp = new HashMap<>();
    
        // 방탐색
        if(!list.isEmpty()) {
            // 서버가 관리하는 세션리스트
            for (HashMap<String, Object> stringObjectHashMap : list) {
                String roomNumber = (String) stringObjectHashMap.get(ROOM_NUMBER); // 세션리스트의 저장된 방번호를 가져와서
                if (roomNumber.equals(rN)) { // 같은 값의 방이 존재한다면
                    temp = stringObjectHashMap; // 해당 방번호의 세션리스트의 존재하는 모든 object값을 가져온다.
                    break;
                }
            }
            sendToRoomMessage(temp, obj);
        }
    }


    // 사용자가 웹소켓 서버에 접속 시 CLIENTS에 담음
    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {

        super.afterConnectionEstablished(session);
        boolean flag = false;

        // url에서 roomNumber추출
        String url = session.getUri().toString();
        String roomNumber = url.split("/api/chat/")[1];

        // 세션리스트에 세션 추가
        int idx = list.size();
        if(!list.isEmpty()) {
            for(int i=0; i<list.size(); i++) {
                String rN = (String) list.get(i).get(ROOM_NUMBER);
                if(rN.equals(roomNumber)) {
                    flag = true;
                    idx = i;
                    break;
                }
            }
        }
        if(flag) {
            HashMap<String, Object> map = list.get(idx);
            map.put(session.getId(), session);
            list.add(map);
        }else {
            HashMap<String, Object> map = new HashMap<>();
            map.put(ROOM_NUMBER, roomNumber);
            map.put(session.getId(), session);
            list.add(map);
        }

        // 등록한 세션 정보 메시지 전송
        JSONObject obj = new JSONObject();
        obj.put("type", "getId");
        obj.put("sessionId", session.getId());
        session.sendMessage(new TextMessage(obj.toJSONString()));
        
        // 채팅방 입장 인사
        sendToSessionMessage(session, " 어서오세요! 채팅방입니다.");
    }

    // 사용자 접속 끊길 시 호출. CLIENTS 객체에서 접속이 끊어진 아이디 값을 제거
    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) throws Exception {

        //소켓이 종료된 세션을 세션리스트에서 삭제
        String rN=null; // 제거할 세션의 방번호
        if(!list.isEmpty()) {
            for(int i=0; i<list.size(); i++) {
                rN  = (String) list.get(i).get(ROOM_NUMBER);
                list.get(i).remove(session.getId()); // sessionId와 일치하는 세션을 제거
            }
        }
        super.afterConnectionClosed(session, status);
        
        // 방에 남은 다른 세션에게 알림 전송
        JSONObject exitMessage = new JSONObject();
        exitMessage.put(ROOM_NUMBER, rN);
        exitMessage.put("message", "상대방이 나갔습니다.");
        HashMap<String, Object> room = new HashMap<>();

        // 서버가 관리하는 세션리스트
        if(!list.isEmpty()) {
            for(int i=0; i<list.size(); i++) {
                String roomNumber = (String) list.get(i).get(ROOM_NUMBER);
                if(roomNumber.equals(rN)) {  // 방에 남은 상대방 세션 object를 room에 가져온다.
                    room = list.get(i);
                    break;
                }
            }
            sendToRoomMessage(room, exitMessage);
        }

    }
    
    // 파싱용 함수
    private static JSONObject jsonToObjectParser(String jsonStr) {
        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        try {
            obj = (JSONObject) parser.parse(jsonStr);

        } catch (ParseException e) {
            logger.error("An error occurred during jsonToObjectParser", e);
        }
        return obj;
    }

    // 서버가 해당 세션으로의 메시지 전송
    private void sendToSessionMessage(WebSocketSession session, String serverMessage) {
        try {
            session.sendMessage(new TextMessage(serverMessage));
        } catch (IOException e) {
            logger.error("An error occurred during afterConnectionEstablished", e);
        }
    }
    
    // 서버가 해당 방내 모든 세션으로 메시지 전송
    private void sendToRoomMessage(HashMap<String, Object> room, JSONObject messageObject) {
        for(String k : room.keySet()) {
            if(k.equals(ROOM_NUMBER)) { // obj내 "message"만 처리할 것
                continue;
            }
            WebSocketSession wss = (WebSocketSession) room.get(k);
            if(wss != null) {
                try {
                    wss.sendMessage(new TextMessage(messageObject.toJSONString()));
                } catch (IOException e) {
                    logger.error("An error occurred during sendToRoomMessage", e);
                }
            }
        }
    }

}