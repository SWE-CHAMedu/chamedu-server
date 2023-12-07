package com.example.chamedu_v1.data.dto;

import lombok.Data;
import java.util.List;

@Data
public class MentorProfileResponseDto {
    private int admissionType;
    private String nickname;
    private String university;
    private int college;
    private float avgScore;
    private int requestRoomCount;
    private String promotionText;
    private String currentChatTime;
    private int reviewCount;
    private List<ReviewMyPageResponseDto> reviewList;
}
