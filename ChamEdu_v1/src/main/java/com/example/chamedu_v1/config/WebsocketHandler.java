package com.example.chamedu_v1.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.chamedu_v1.service.ChatManageService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import com.example.chamedu_v1.service.ChatManageService;
import java.util.concurrent.TimeUnit;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Log4j2
public class WebsocketHandler extends TextWebSocketHandler{

    private int INITIAL_TIME_IN_SECONDS  = 30 * 60; // 남은 시간 (30분=1800초로 초기화)
    private int remainingTimeInSeconds = INITIAL_TIME_IN_SECONDS;
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    List<HashMap<String,Object>> list = new ArrayList<>();

    // 사용자의 메시지를 받으면 동작. CLIENTS 객체에 담긴 세션 값들을 가져와서 반복문을 통해 메시지를 발송
    @Override
    protected void handleTextMessage(WebSocketSession session, @NotNull TextMessage message) throws Exception {

        String msg = message.getPayload();
        JSONObject obj = jsonToObjectParser(msg);

        String rN = (String) obj.get("roomNumber");
        HashMap<String, Object> temp = new HashMap<String,Object>();
    
        // 방탐색
        if(list.size() > 0) {
            // 서버가 관리하는 세션리스트
            for(int i=0; i<list.size(); i++) {
                String roomNumber = (String) list.get(i).get("roomNumber"); // 세션리스트의 저장된 방번호를 가져와서
                if(roomNumber.equals(rN)) { // 같은 값의 방이 존재한다면
                    temp = list.get(i); // 해당 방번호의 세션리스트의 존재하는 모든 object값을 가져온다.
                    break;
                }
            }
            sendToRoomMessage(temp, obj);
        }
    }


    // 사용자가 웹소켓 서버에 접속 시 CLIENTS에 담음
    @SuppressWarnings("unchecked")
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        // 타이머 시작 ( 최하단 startTimer 참조 )
        // startTimer(session);

        // 웹소켓 세션 추가
        super.afterConnectionEstablished(session);
        boolean flag = false;

        String url = session.getUri().toString();
        String roomNumber = url.split("/chat/")[1];

        // 방 탐색
        int idx = list.size();
        if(list.size() > 0) {
            for(int i=0; i<list.size(); i++) {
                String rN = (String) list.get(i).get("roomNumber");
                if(rN.equals(roomNumber)) {
                    flag = true;
                    idx = i;
                    break;
                }
            }
        }

        if(flag) { //존재하는 방이라면 세션만 추가한다.
            HashMap<String, Object> map = list.get(idx);
            map.put(session.getId(), session);
            list.add(map);
        }else { // 최초 생성하는 방이라면 방번호와 세션을 추가한다.
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("roomNumber", roomNumber);
            map.put(session.getId(), session);
            list.add(map);
        }

        // 등록한 세션 정보 전송
        JSONObject obj = new JSONObject();
        obj.put("type", "getId");
        obj.put("sessionId", session.getId());
        session.sendMessage(new TextMessage(obj.toJSONString()));

        sendToSessionMessage(session, " 어서오세요! 채팅방입니다.");
    }

    // 사용자 접속 끊길 시 호출. CLIENTS 객체에서 접속이 끊어진 아이디 값을 제거
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        //소켓이 종료된 세션을 세션리스트에서 삭제
        String rN=null;
        if(list.size() > 0) { 
            for(int i=0; i<list.size(); i++) {
                rN = (String) list.get(i).get("roomNumber");
                list.get(i).remove(session.getId());
            }
        }
        super.afterConnectionClosed(session, status);
        
        // 방에 남은 다른 세션에게 알림 전송
        JSONObject exitMessage = new JSONObject();
        exitMessage.put("roomNumber", rN);
        exitMessage.put("message", "상대방이 나갔습니다.");
        HashMap<String, Object> room = new HashMap<String,Object>();
        if(list.size() > 0) {
            // 서버가 관리하는 세션리스트
            for(int i=0; i<list.size(); i++) {
                String roomNumber = (String) list.get(i).get("roomNumber"); // 세션리스트의 저장된 방번호를 가져와서
                if(roomNumber.equals(rN)) { // 같은 값의 방이 존재한다면
                    room = list.get(i); // 해당 방번호의 세션리스트의 존재하는 모든 object값을 가져온다.
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
            e.printStackTrace();
        }
        return obj;
    }

    // 타이머
    private void startTimer(WebSocketSession session) {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                // 클라이언트로 남은 시간 전송 (1초마다)
                remainingTimeInSeconds-=60;
                sendToSessionMessage(session, String.valueOf(remainingTimeInSeconds));

                // 시간이 다 되면 웹소켓 세션 종료
                if (remainingTimeInSeconds <= 0) {
                    sendToSessionMessage(session, " 시간이 만료되어 채팅이 불가능합니다.");
                    session.close(CloseStatus.NORMAL);
                    scheduler.shutdown();
                }
            } catch (IOException e) {
                e.printStackTrace();
                scheduler.shutdown();
            }
        }, 0, 60, TimeUnit.SECONDS);
    }

    // 서버가 해당 세션으로의 메시지 전송
    private void sendToSessionMessage(WebSocketSession session, String serverMessage) {
        try {
            session.sendMessage(new TextMessage(serverMessage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // 서버가 해당 방내 모든 세션으로 메시지 전송
    private void sendToRoomMessage(HashMap<String, Object> room, JSONObject messageObject) {
        for(String k : room.keySet()) {
            if(k.equals("roomNumber")) { // obj내 "message"만 처리할 것
                continue;
            }
            WebSocketSession wss = (WebSocketSession) room.get(k);
            if(wss != null) {
                try {
                    wss.sendMessage(new TextMessage(messageObject.toJSONString()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}