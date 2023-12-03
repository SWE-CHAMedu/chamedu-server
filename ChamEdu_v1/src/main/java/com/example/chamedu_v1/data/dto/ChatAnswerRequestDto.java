package com.example.chamedu_v1.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChatAnswerRequestDto {
    private int roomId;
    private boolean answer; // 수락시 true 거절시 false
}
