package com.example.chamedu_v1.data.repository;


import com.example.chamedu_v1.data.entity.Profile;
import com.example.chamedu_v1.data.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface
ProfileRepository extends JpaRepository<Profile,Integer> {

    Profile save(Profile profile);

    @Query("SELECT Profile FROM Profile profile WHERE profile.mentor.userId =: userId ")
    Profile findByMentor(@Param("userId") String userId);

    @Query("SELECT Room FROM Room room WHERE room.mentor.userId =: userId order by room.startDate ASC")
    Room findByStartDate(@Param("userId") String userId);


}
