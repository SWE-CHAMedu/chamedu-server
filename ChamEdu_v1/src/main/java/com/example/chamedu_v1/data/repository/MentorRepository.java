package com.example.chamedu_v1.data.repository;
import com.example.chamedu_v1.data.entity.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorRepository extends JpaRepository<Mentor, Integer> {
    Mentor findByMentorId(int menteeId);
    Mentor findByUserId(String userId);
    boolean existsByUserId(String userId);
    boolean existsByNickname(String nickname);
}
