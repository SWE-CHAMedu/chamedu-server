package com.example.chamedu_v1.data.repository;


import com.example.chamedu_v1.data.entity.Profile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface
ProfileRepository extends JpaRepository<Profile,Integer> {

    Profile findByMentorMentorId(int mentorId);
    List<Profile> findAllByOrderByMentorChatCountDesc(Pageable pageable);
    List<Profile> findByAdmissionType(int wishAdmissionType,Pageable pageable);
    List<Profile> findByCollege(int wishCollege,Pageable pageable);

    Page<Profile> findAllByOrderByMentorMentorIdDesc(Pageable pageable);

    Profile save(Profile profile);

    Profile findByMentor_UserId(String userId);

}
