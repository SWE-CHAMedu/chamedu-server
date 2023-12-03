package com.example.chamedu_v1.data.dto;

import com.example.chamedu_v1.data.entity.Mentee;
import com.example.chamedu_v1.data.entity.Mentor;
import com.example.chamedu_v1.data.entity.Room;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
public class RoomDto {

    private Mentee mentee;
    private Mentor mentor;

    private Date startDate;
    private Date endDate;

    private char status;

    private String chatTitle;

    public RoomDto(Room saveRoom){

        mentee=saveRoom.getMentee();
        mentor=saveRoom.getMentor();
        startDate=saveRoom.getStartDate();
        endDate=saveRoom.getEndDate();
        status=saveRoom.getStatus();
        chatTitle=saveRoom.getChatTitle();
    }
}
