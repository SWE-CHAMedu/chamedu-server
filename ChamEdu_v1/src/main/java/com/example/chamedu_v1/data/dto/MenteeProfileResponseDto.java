package com.example.chamedu_v1.data.dto;

import lombok.Data;

import java.util.List;

@Data
public class MenteeProfileResponseDto {

    private int userImg;
    private String nickname;
    private int wishCollege;
    private String promotionText;
    private int endChatCount;
    private int reviewCount;
    private String currentChatTime;
    private List<RoomMyPageResponseDto> reqeustRoomList;
}
