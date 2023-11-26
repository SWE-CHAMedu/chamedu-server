package com.example.chamedu_v1.data.dto;
import com.example.chamedu_v1.data.entity.Mentor;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MentorSignUpDto {
    private int mentorId;
    private String userId;
    private String address;
    private String email;
    private String name;
    private String nickname;
    private String password;
    private String university;
    private String userImg;

    public Mentor toEntity() {
        return Mentor.builder()
                .mentorId(this.getMentorId())
                .userId(this.getUserId())
                .address(this.getAddress())
                .email(this.getEmail())
                .name(this.getName())
                .nickname(this.getNickname())
                .password(this.getPassword())
                .university(this.getUniversity())
                .userImg(this.getUserImg())
                .build();
    }

    public void setMentorId(int mentorId) {this.mentorId = mentorId;}
    public void setUserId(String userId) { this.userId = userId; }
    public void setAddress(String address) { this.address = address; }
    public void setEmail(String email) { this.email = email;  }
    public void setName(String name) { this.name = name; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public void setPassword(String password) { this.password = password;  }
    public void setUniversity(String university) {  this.university = university;  }
    public void setUserImg(String userImg) {  this.userImg = userImg;  }
}



