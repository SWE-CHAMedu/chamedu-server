package com.example.chamedu_v1.data.dto;


import com.example.chamedu_v1.data.entity.Mentor;
import com.example.chamedu_v1.data.entity.Profile;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class MentorProfileListDto {

    private List<MentorProfileDto> popularMentors;
    private List<MentorProfileDto> wishAdmissionTypeMentors;
    private List<MentorProfileDto> wishCollegeMentors;
    // Convert a list of Profile to a list of MentorProfileDto
    private List<MentorProfileDto> convertToDtoList(List<Profile> profiles) {
        return profiles.stream().map(MentorProfileDto::new).collect(Collectors.toList());
    }

    public MentorProfileListDto(List<Profile> popularMentors, List<Profile> wishAdmissionTypeMentors, List<Profile> wishCollegeMentors) {
        this.popularMentors = convertToDtoList(popularMentors);
        this.wishAdmissionTypeMentors = convertToDtoList(wishAdmissionTypeMentors);
        this.wishCollegeMentors = convertToDtoList(wishCollegeMentors);
    }

//
//    public MentorProfileListDto(List<Mentor> likeMentors, List<UserLikeMentor> userLikeMentors) {
//        this.popularMentorList = mapToPopularMentorDtoList(likeMentors, userLikeMentors);
//        this.wishAddmissionTypeMentorList = mapTowishAddmissionTypeMentorDtpList(likeMentors, userLikeMentors);
//        this.wishCollegeMentorList = mapTowishCollegeMentorDtoList(likeMentors, userLikeMentors);
//    }
//
}
