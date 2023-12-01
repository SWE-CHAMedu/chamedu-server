package com.example.chamedu_v1.service;

import com.example.chamedu_v1.data.dto.MenteeJoinRequestDto;
import com.example.chamedu_v1.data.entity.Mentee;
import com.example.chamedu_v1.data.repository.MenteeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenteeAccessServiceImpl implements MenteeAccessService{

    private final MenteeRepository menteeRepository;

    @Autowired
    public MenteeAccessServiceImpl(MenteeRepository menteeRepository) {
        this.menteeRepository = menteeRepository;
    }

    @Override
    @Transactional
    public void registerMentee(MenteeJoinRequestDto menteeJoinRequestDto) {
        Mentee mentee = new Mentee();
        mentee.setUserId(menteeJoinRequestDto.getUserId());
        mentee.setPassword(menteeJoinRequestDto.getPassword());
        mentee.setNickname(menteeJoinRequestDto.getNickname());
        mentee.setName(menteeJoinRequestDto.getName());
        menteeRepository.save(mentee);
    }

    @Override
    public boolean authenticateUser(Mentee mentee) {
        Mentee storedMentee = menteeRepository.findByUserId(mentee.getUserId());
        return storedMentee != null && storedMentee.getPassword().equals(mentee.getPassword());
    }
}
