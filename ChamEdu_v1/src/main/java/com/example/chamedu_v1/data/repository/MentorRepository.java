package com.example.chamedu_v1.data.repository;
import com.example.chamedu_v1.data.entity.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MentorRepository extends JpaRepository<Mentor,Long> {

    Mentor findByUserId(String userId);

    Mentor findByMentorId(int mentorId);

    @Query("SELECT count(Review) FROM Review review WHERE review.mentor.userId =: userId")
    int findReviewCountByMentorId(@Param("userId") String userId);

    Mentor save(Mentor mentor);
}
