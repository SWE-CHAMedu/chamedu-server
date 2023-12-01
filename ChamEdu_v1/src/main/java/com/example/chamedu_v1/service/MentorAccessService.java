package com.example.chamedu_v1.service;

import com.example.chamedu_v1.data.dto.MentorJoinRequestDto;
import com.example.chamedu_v1.data.entity.Mentor;


public interface MentorAccessService {
    void registerMentor(MentorJoinRequestDto mentorJoinRequestDto);
    boolean authenticateUser(Mentor mentor);
}