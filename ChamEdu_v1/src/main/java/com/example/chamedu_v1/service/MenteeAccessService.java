package com.example.chamedu_v1.service;

import com.example.chamedu_v1.data.dto.MenteeJoinRequestDto;
import com.example.chamedu_v1.data.entity.Mentee;

public interface MenteeAccessService {
    void registerMentee(MenteeJoinRequestDto menteeJoinRequestDto);
    boolean authenticateUser(Mentee mentee);
}