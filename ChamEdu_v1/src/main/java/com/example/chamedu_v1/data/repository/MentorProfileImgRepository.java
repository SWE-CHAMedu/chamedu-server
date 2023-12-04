package com.example.chamedu_v1.data.repository;

import com.example.chamedu_v1.data.entity.MentorImageFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorProfileImgRepository extends JpaRepository<MentorImageFile,Integer> {

    MentorImageFile findByMentor_UserId(String userId);
}
