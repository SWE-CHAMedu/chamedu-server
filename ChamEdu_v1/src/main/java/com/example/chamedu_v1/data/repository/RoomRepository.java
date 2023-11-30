package com.example.chamedu_v1.data.repository;

import com.example.chamedu_v1.data.entity.Review;
import com.example.chamedu_v1.data.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Integer> {

    @Query("SELECT Room FROM Room room WHERE room.mentee.menteeId =: menteeId")
    List<Room> findAllByMenteeId(@Param("menteeId") int menteeId);


    @Query("SELECT Room FROM Room room WHERE room.mentor.mentorId =: mentorId")
    List<Room> findAllByMentor(@Param("mentorId") int mentorId);

}
