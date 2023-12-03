package com.example.chamedu_v1.data.dto;

import com.example.chamedu_v1.data.entity.Mentor;
import com.example.chamedu_v1.data.entity.Profile;
import com.example.chamedu_v1.data.entity.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class MentorProfileDetailDto {

    private String profileImg;
    private String nickname;

    private String university;
    private int addmissionType;

    private String promotonText;

    //구현방법 고민해봐야함
    //평균 별점
    private float avgScore;

    //진행한 상담 수 - 고민해봐야함
    private int chatCount;
    private int reviewCount;
    private List<ReviewDto> reviewList;

    public MentorProfileDetailDto(Profile profile) {
        profileImg = profile.getProfileImg();
        nickname = profile.getMentor().getNickname();

        university= profile.getUniversity();
        addmissionType = profile.getAdmissionType();
        promotonText = profile.getPromotionText();

        avgScore = profile.getAvgScore();
        chatCount=profile.getMentor().getChatCount();

        reviewList = new ArrayList<>();
        for (Review review : profile.getMentor().getReviewList()) {
            reviewList.add(new ReviewDto(review));
        }
        reviewCount=reviewList.size();
    }
}
