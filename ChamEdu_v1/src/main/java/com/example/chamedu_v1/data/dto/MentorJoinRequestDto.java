package com.example.chamedu_v1.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MentorJoinRequestDto {
    private String userId;
    private String password;
    private String nickname;
    private String name;
    private String university;
    private String userImg;
}
