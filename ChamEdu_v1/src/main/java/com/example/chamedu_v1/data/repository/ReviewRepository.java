package com.example.chamedu_v1.data.repository;

import com.example.chamedu_v1.data.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("SELECT Review From Review review WHERE review.mentor.mentorId=:mentorId")
    List<Review> findAllByMentor(@Param("mentorId") int mentorId);


}
