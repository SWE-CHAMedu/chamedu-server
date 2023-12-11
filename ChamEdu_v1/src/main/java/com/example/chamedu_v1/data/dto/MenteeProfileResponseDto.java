package com.example.chamedu_v1.data.dto;

import lombok.Data;
import java.util.List;

@Data
public class MenteeProfileResponseDto {

    private String nickname;
    private int admissionType;
    private String university;
    private String promotionText;
    private int endChatCount;
    private int reviewCount;
    private String currentChatTime;
    private List<RoomMyPageResponseDto> reqeustRoomList;
}
