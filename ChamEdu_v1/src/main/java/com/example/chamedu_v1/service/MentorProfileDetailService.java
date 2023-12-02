package com.example.chamedu_v1.service;

import com.example.chamedu_v1.data.dto.MentorProfileDetailDto;
import com.example.chamedu_v1.data.entity.Profile;
import com.example.chamedu_v1.data.entity.Review;
import com.example.chamedu_v1.data.repository.ProfileRepository;
import com.example.chamedu_v1.data.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MentorProfileDetailService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ReviewRepository reviewRepository;


    public float calculateAverageStarsByMentorId(int mentorId) {
        List<Review> reviews = reviewRepository.findReviewsByMentorMentorId(mentorId);
        if (reviews.isEmpty()) {
            return 0.0f; // 리뷰가 없는 경우 기본값 반환
        }
        float sum = 0.0f;
        for (Review review : reviews) {
            sum += review.getScore(); // 리뷰에서 별점 추출
        }
        return sum / reviews.size(); // 별점의 평균 반환
    }

    @Transactional
    public MentorProfileDetailDto getMentorProfileDetail(int mentorId){
        Profile profile = profileRepository.findByMentorMentorId(mentorId);
        float avgScore = calculateAverageStarsByMentorId(mentorId);
        profile.setAvgScore(avgScore);
        return new MentorProfileDetailDto(profile);
    }

}
