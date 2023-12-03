package com.example.chamedu_v1.data.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class RoomMyPageResponseDto {

    private int roomId;
    private String mentorName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String title;
    private char status;
}
