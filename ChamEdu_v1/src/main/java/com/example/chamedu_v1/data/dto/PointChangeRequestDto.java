package com.example.chamedu_v1.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class PointChangeRequestDto {
    private String userId; // 사용자 정보
    private int changedPoints; // 구매량 or 환불량
}
