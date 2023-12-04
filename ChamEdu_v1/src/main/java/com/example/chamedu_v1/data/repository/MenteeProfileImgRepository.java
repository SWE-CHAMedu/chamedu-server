package com.example.chamedu_v1.data.repository;

import com.example.chamedu_v1.data.entity.MenteeImageFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenteeProfileImgRepository extends JpaRepository<MenteeImageFile, Integer> {

    MenteeImageFile findByMentee_UserId(String userId);
}
