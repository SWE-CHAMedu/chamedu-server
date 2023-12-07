package com.example.chamedu_v1.data.dto;


import com.example.chamedu_v1.data.entity.Room;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class RoomDto {

    //멘티, 멘토 객체 저장 X
    private int menteeId;
    private int mentorId;

    private Date startDate;
    private Date endDate;

    private char status;

    private String chatTitle;

    public RoomDto(Room saveRoom,int _mentorId, int _menteeId){

        menteeId=_menteeId;
        mentorId=_mentorId;

        startDate=saveRoom.getStartDate();
        endDate=saveRoom.getEndDate();
        status=saveRoom.getStatus();
        chatTitle=saveRoom.getChatTitle();
    }
}
