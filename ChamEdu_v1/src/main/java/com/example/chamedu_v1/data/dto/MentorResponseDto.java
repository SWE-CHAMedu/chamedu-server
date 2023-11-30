package com.example.chamedu_v1.data.dto;

import com.example.chamedu_v1.data.entity.Mentor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MentorResponseDto {
    private int mentorId;
    private String userId;
    private String address;
    private int point;
    private String name;
    private String nickname;
    private String password;
    private String university;
    private String userImg;



    public MentorResponseDto(Mentor mentor) {
        mentorId = mentor.getMentorId();
        userId = mentor.getUserId();
        address = mentor.getPassword();
        point = mentor.getPoint();
        name = mentor.getName();
        nickname = mentor.getNickname();
        password = mentor.getPassword();
        university = mentor.getUniversity();
        userImg = mentor.getUserImg();

    }
}