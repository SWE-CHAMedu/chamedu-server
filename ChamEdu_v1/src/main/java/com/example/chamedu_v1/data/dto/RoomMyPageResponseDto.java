package com.example.chamedu_v1.data.dto;

import lombok.Data;

import java.util.Date;

@Data
public class RoomMyPageResponseDto {

    private int roomId;
    private String mentorName;
    private Date startTime;
    private Date endTime;
    private String title;
    private char status;
}
