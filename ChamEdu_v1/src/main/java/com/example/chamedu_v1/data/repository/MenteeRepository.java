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


    @Query("SELECT Room FROM Room room WHERE room.mentee.userId =: userId order by room.startDate ASC")
    Room findRoomStarDateByUserId(@Param("userId") String userId);

    @Query("SELECT count(room) FROM Room room WHERE room.startDate < CURRENT_DATE AND room.mentee.userId = :userId")
    int findRoomCountByUserId(@Param("userId") String userId);

    @Query("SELECT count(Review) FROM Review review WHERE review.mentee.userId =: userId")
    int findReviewCountByUserId(@Param("userId") String userId);

}
