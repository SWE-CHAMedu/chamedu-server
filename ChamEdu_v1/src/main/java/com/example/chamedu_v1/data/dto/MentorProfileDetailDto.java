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

    private String nickname;

    private String university;
    private int college;
    private int addmissionType;

    private String promotonText;

    private float avgScore;

    private int chatCount;
    private int reviewCount;
    private List<ReviewDto> reviewList;

    public MentorProfileDetailDto(Profile profile) {
        nickname = profile.getMentor().getNickname();

        university= profile.getUniversity();
        college=profile.getCollege();
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
