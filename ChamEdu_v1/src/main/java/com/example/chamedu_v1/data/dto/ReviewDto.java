package com.example.chamedu_v1.data.dto;


import com.example.chamedu_v1.data.entity.Review;
import lombok.*;


@Getter
@NoArgsConstructor
public class ReviewDto {
    private int mentorId;
    private int menteeId;
    private String title;
    private String content;
    private int score;

    public ReviewDto(Review review){
        mentorId=review.getMentor().getMentorId();
        menteeId=review.getMentee().getMenteeId();
        title=review.getTitle();
        content=review.getContent();
        score=review.getScore();

    }
    public ReviewDto(Review review, int _mentorId, int _menteeId){
        menteeId=_menteeId;
        mentorId=_mentorId;
        title=review.getTitle();
        content=review.getContent();
        score=review.getScore();
    }
}
