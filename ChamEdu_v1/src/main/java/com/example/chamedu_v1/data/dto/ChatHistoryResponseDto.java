package com.example.chamedu_v1.data.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ChatHistoryResponseDto {
    private int roomId;
    private String userName;
    private Date startTime;
    private Date endTime;
    private String title;
    private char checkStatus;
}
