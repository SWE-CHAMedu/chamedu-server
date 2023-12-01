package com.example.chamedu_v1.service;

import com.example.chamedu_v1.data.dto.MentorJoinRequestDto;
import com.example.chamedu_v1.data.entity.Mentor;

public interface MentorJoinService {
    void registerMentor(MentorJoinRequestDto mentor);
    boolean authenticateUser(Mentor mentor);
}
