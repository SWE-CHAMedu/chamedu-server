package com.example.chamedu_v1.data.repository;

import com.example.chamedu_v1.data.entity.Review;
import com.example.chamedu_v1.data.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Integer> {


    List<Room> findAllByMentee_UserId(String userId);

    List<Room> findAllByMentor_UserId(String userId);

    Room findByMentee_UserIdOrderByStartDateAsc(String userId);

    Room findByMentor_UserIdOrderByStartDateAsc(String userId);

    int countByMentee_UserId(String userId);


}
