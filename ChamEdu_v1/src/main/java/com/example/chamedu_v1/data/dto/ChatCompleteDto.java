package com.example.chamedu_v1.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChatCompleteDto {
    private int roomId;
    private String mentor_userId;
    private String mentee_userId;
}
