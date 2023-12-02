package com.example.chamedu_v1.data.repository;

import com.example.chamedu_v1.data.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {


    List<Review> findReviewsByMentorMentorId(int mentorId);
    List<Review> findAllByMentor_UserId(String userId);

    int countByMentor_UserId(String userId);

    int countByMentee_UserId(String userId);

}
