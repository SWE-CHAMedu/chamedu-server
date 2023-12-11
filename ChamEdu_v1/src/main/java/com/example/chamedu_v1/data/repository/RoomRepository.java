package com.example.chamedu_v1.data.repository;

import com.example.chamedu_v1.data.entity.Room;
import com.example.chamedu_v1.data.entity.Mentor;
import com.example.chamedu_v1.data.entity.Mentee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Integer> {


    List<Room> findAllByMentee_UserIdOrderByStartDate(String userId);

    List<Room> findAllByMentor_UserId(String userId);
    List<Room> findAllByMentor_UserIdAndStatusOrderByStartDate(String userId, char status);

    Room findFirstByMentee_UserIdOrderByStartDateDesc(String userId);
    Room findFirstByMentor_UserIdOrderByStartDateDesc(String userId);

    int countByMentor_UserId(String userId);
    int countByMentee_UserId(String userId);

    Room findByRoomId(int roomId);


    // 신고 시 사용
    @Query("SELECT r.mentor FROM Room r WHERE r.roomId = :roomId")
    Mentor findMentorIdByRoomId(@Param("roomId") int roomId);

    @Query("SELECT r.mentee FROM Room r WHERE r.roomId = :roomId")
    Mentee findMenteeIdByRoomId(@Param("roomId") int roomId);
    @Transactional
    Room save(Room room);


}
