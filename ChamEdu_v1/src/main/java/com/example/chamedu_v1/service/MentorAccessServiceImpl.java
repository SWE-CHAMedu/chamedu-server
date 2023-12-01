package com.example.chamedu_v1.service;

import com.example.chamedu_v1.data.dto.MentorJoinRequestDto;
import com.example.chamedu_v1.data.entity.Mentor;
import com.example.chamedu_v1.data.repository.MentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MentorAccessServiceImpl implements MentorAccessService {

    private final MentorRepository mentorRepository;

    @Autowired
    public MentorAccessServiceImpl(MentorRepository mentorRepository) {
        this.mentorRepository = mentorRepository;
    }

    @Override
    @Transactional
    public void registerMentor(MentorJoinRequestDto mentorJoinRequestDto) {
        Mentor mentor = new Mentor();
        mentor.setUserId(mentorJoinRequestDto.getUserId());
        mentor.setPassword(mentorJoinRequestDto.getPassword());
        mentor.setNickname(mentorJoinRequestDto.getNickname());
        mentor.setName(mentorJoinRequestDto.getName());
        mentor.setUniversity(mentorJoinRequestDto.getUniversity());
        mentor.setUserImg(mentorJoinRequestDto.getUserImg());
        mentorRepository.save(mentor);
    }

    @Override
    public boolean authenticateUser(Mentor mentor) {
        Mentor storedMentor = mentorRepository.findByUserId(mentor.getUserId());
        return storedMentor != null && storedMentor.getPassword().equals(mentor.getPassword());
    }
}