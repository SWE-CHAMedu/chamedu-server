package com.example.chamedu_v1.data.dto;

import com.example.chamedu_v1.data.entity.Mentor;
import com.example.chamedu_v1.data.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
public class MentorProfileResponseDto {
    private String userImg;
    private int admissionType;
    private String nickname;
    private String university;
    private float avgScore;
    private int requestRoomCount;
    private String promotionText;
    private String currentChatTime;
    private int reviewCount;
    private List<ReviewMyPageResponseDto> reviewList;
}
