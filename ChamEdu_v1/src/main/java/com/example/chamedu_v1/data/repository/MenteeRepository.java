package com.example.chamedu_v1.data.repository;

import com.example.chamedu_v1.data.entity.Mentee;
import com.example.chamedu_v1.data.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MenteeRepository extends JpaRepository<Mentee,Integer> {
    Mentee findByUserId(String userId);

    Mentee findByMenteeId(int menteeId);

    @Query("SELECT Room FROM Room room WHERE room.mentee.menteeId =: menteeId order by room.startDate ASC")
    Room findRoomStarDateByMenteeId(@Param("menteeId") int menteeId);

    @Query("SELECT count(room) FROM Room room WHERE room.startDate < CURRENT_DATE AND room.mentee.menteeId = :menteeId")
    int findRoomCountByMenteeId(@Param("menteeId") int menteeId);

    @Query("SELECT count(Review) FROM Review review WHERE review.mentee.menteeId =: menteeId")
    int findReviewCountByMenteeId(@Param("menteeId") int menteeId);

}
