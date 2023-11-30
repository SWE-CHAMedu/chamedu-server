package com.example.chamedu_v1.data.repository;
import com.example.chamedu_v1.data.entity.Mentor;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/*
public interface MentorRepository extends JpaRepository<Mentor, Integer> {
    Mentor findByMentorId(int menteeId);
    Mentor findByUserId(String userId);
    boolean existsByUserId(String userId);
    boolean existsByNickname(String nickname);
}
*/

@Repository
@RequiredArgsConstructor
public class MentorRepository  {

    private final EntityManager em;

    // 멤버 저장하는 쿼리
    public void saveMember(Mentor mentor){
        em.persist(mentor);
    }

    // 하나의 id찾기
    public Mentor findOne(Long mentorid){
        return em.find(Mentor.class, mentorid);
    }

    // 전체 찾기
    public List<Mentor> findAll(){
        return em.createQuery("select m from Mentor m")
                .getResultList();
    }

    // 일치하는 이름(아이디) 찾는 메소드
    public List<Mentor> findByUserId(String userId) {
        return em.createQuery("select m from Mentor m where m.userId = :userId", Mentor.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}