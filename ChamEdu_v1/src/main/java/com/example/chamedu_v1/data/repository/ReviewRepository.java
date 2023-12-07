package com.example.chamedu_v1.data.repository;

import com.example.chamedu_v1.data.entity.Review;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    @Query("SELECT COALESCE(AVG(review.score), 0) FROM Review review WHERE review.mentor.userId = :userId")
    float findAveragePointByMentorUserId(@Param("userId") String userId);
    @Transactional
    void deleteById(int id);


    Page<Review> findByMenteeMenteeId(Pageable pageable, int menteeId);
}
