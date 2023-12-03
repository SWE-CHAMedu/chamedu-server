package com.example.chamedu_v1.data.repository;

import com.example.chamedu_v1.data.entity.Review;
import com.example.chamedu_v1.data.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Integer> {


    List<Room> findAllByMentee_UserId(String userId);

    List<Room> findAllByMentor_UserId(String userId);

    Room findFirstByMentee_UserIdOrderByStartDateDesc(String userId);

    Room findFirstByMentor_UserIdOrderByStartDateDesc(String userId);

    int countByMentor_UserId(String userId);

    int countByMentee_UserId(String userId);

    Room findByRoomId(int roomId);

    @Transactional
    Room save(Room room);

}
