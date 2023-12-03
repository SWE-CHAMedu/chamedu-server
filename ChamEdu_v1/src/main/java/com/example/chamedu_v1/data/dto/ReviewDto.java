package com.example.chamedu_v1.data.dto;


import com.example.chamedu_v1.data.entity.Review;
import com.example.chamedu_v1.data.entity.Mentee;
import com.example.chamedu_v1.data.entity.Mentor;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor
public class ReviewDto {
    //private int reviewId;
    private int mentorId;
    private int menteeId;
    private String title;
    private String content;
    private int score;


    public ReviewDto(Review review){
        //reviewId=review.getReviewId();
        mentorId=review.getMentor().getMentorId();
        menteeId=review.getMentee().getMenteeId();
        title=review.getTitle();
        content=review.getContent();
        score=review.getScore();

    }
}
