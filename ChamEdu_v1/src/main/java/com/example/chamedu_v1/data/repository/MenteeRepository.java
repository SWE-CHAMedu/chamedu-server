package com.example.chamedu_v1.data.repository;

import com.example.chamedu_v1.data.entity.Mentee;
import com.example.chamedu_v1.data.entity.Room;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MenteeRepository extends JpaRepository<Mentee,Integer> {
    Mentee findByUserId(String userId);
    Mentee findByMenteeId(int menteeId);
    @Transactional
    Mentee save(Mentee mentee);


}
