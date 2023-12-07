package com.example.chamedu_v1.data.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ChatHistoryResponseDto {
    private int roomId;
    private String userName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String title;
    private String checkStatus;
}
