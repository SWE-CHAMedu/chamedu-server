package com.example.chamedu_v1.data.repository;
import com.example.chamedu_v1.data.entity.Mentor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;

@Repository
public interface MentorRepository extends JpaRepository<Mentor,Long> {

    Mentor findByUserId(String userId);
    Mentor findByMentorId(int mentorId);

    @Transactional
    Mentor save(Mentor mentor);

    @Query("SELECT m.availableTime FROM Mentor m WHERE m.mentorId = :mentorId")
    List<Time> findAvailableTimeByMentorId(@Param("mentorId") int mentorId);
}
