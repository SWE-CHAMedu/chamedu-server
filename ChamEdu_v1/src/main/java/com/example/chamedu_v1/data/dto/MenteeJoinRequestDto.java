package com.example.chamedu_v1.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MenteeJoinRequestDto {
    private String name;
    private String nickname;
    private String userId;
    private String password;
    private String profileImg;

}