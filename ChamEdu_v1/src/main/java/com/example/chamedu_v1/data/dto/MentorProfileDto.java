package com.example.chamedu_v1.data.dto;

import com.example.chamedu_v1.data.entity.Mentor;
import com.example.chamedu_v1.data.entity.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MentorProfileDto {
    private int mentorId;
    private String nickname;
    private int admissionType;
    private String university;
    private int college;

    public MentorProfileDto(Profile profile) {
        mentorId=profile.getMentor().getMentorId();
        nickname = profile.getMentor().getNickname();
        admissionType = profile.getAdmissionType();
        university = profile.getUniversity();
        college = profile.getCollege();

    }
}
