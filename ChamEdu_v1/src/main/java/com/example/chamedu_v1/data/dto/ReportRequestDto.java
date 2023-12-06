package com.example.chamedu_v1.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReportRequestDto {
    private String userId; // 사용자 정보
    private int roomId; // 채팅방 정보
    private String reportDetail; // 신고 내용
}
