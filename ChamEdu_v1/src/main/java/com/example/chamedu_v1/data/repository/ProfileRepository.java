package com.example.chamedu_v1.data.repository;


import com.example.chamedu_v1.data.entity.Profile;
import com.example.chamedu_v1.data.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface
ProfileRepository extends JpaRepository<Profile,Integer> {

    Profile findByMentorMentorId(int mentorId);
    //Profile findAllByMentorChatCountDesc(Pageable pageable);
    Page<Profile> findAllByOrderByMentorMentorIdDesc(Pageable pageable);

    Profile save(Profile profile);

    Profile findByMentor_UserId(String userId);

}
