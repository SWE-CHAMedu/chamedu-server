package com.example.chamedu_v1.data.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class ChatRequestDto {
    Date wishChatSchedule;
    String chatTitle;
}
