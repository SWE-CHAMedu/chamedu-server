package com.example.chamedu_v1.service;

import com.example.chamedu_v1.data.dto.MentorJoinRequestDto;
import com.example.chamedu_v1.data.entity.Mentor;
import com.example.chamedu_v1.data.repository.MentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MentorJoinServiceImpl implements MentorJoinService {

    private final MentorRepository mentorRepository;

    @Autowired
    public MentorJoinServiceImpl(MentorRepository mentorRepository) {
        this.mentorRepository = mentorRepository;
    }

    @Override
    @Transactional
    public void registerMentor(MentorJoinRequestDto mentorJoinRequestDto) {
        Mentor mentor = new Mentor();
        mentor.setUserId(mentorJoinRequestDto.getUserId());
        mentor.setPassword(mentorJoinRequestDto.getPassword());
        mentor.setNickname(mentorJoinRequestDto.getNickname());
        mentorRepository.save(mentor);
    }

    @Override
    public boolean authenticateUser(Mentor mentor) {
        Mentor storedMentor=mentorRepository.findByUserId(mentor.getUserId());
        return storedMentor != null && storedMentor.getPassword().equals(mentor.getPassword());
    }
}
