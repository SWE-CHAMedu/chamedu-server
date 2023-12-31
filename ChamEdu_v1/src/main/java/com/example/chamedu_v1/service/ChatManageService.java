package com.example.chamedu_v1.service;


import java.time.Duration;
import java.time.ZoneId;

import com.example.chamedu_v1.data.entity.Mentee;
import com.example.chamedu_v1.data.entity.Room;
import com.example.chamedu_v1.data.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Component
@Service
public class ChatManageService {
    private static final Logger logger = LoggerFactory.getLogger(ChatManageService.class);

    @Autowired
    private RoomRepository roomRepository;


    // 1. 현재가 채팅이 가능한 시간인지 확인
    public boolean checkChatNow(int roomId) {

        Room room= roomRepository.findByRoomId(roomId);
        if (room==null){
            logger.error("잘못된 방입니다 RoomId: {}", roomId);
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = convertDateToLocalDateTime(room.getStartDate());
        long minutesDifference = Math.abs(Duration.between(startDate, now).toMinutes());

        return minutesDifference <= 30 && now.isAfter(startDate);
    }

    private LocalDateTime convertDateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }


    // 2. 멘티가 돈을 가지고 있는지 확인하고 포인트차감
    public boolean checkPointMentee(int roomId) {

        Room room= roomRepository.findByRoomId(roomId);
        Mentee mentee=room.getMentee();
        if (mentee.getPoint()<=200){
            logger.error("잔액 부족 : 현재 잔액 {}참", mentee.getPoint());
            return false;
        }
        else {
            mentee.setPoint(mentee.getPoint()-200);
            return true;
        }
    }
}

