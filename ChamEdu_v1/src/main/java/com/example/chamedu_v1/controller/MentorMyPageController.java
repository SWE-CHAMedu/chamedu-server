package com.example.chamedu_v1.controller;

import com.example.chamedu_v1.common.FindUserInfo;
import com.example.chamedu_v1.data.dto.MentorProfileResponseDto;
import com.example.chamedu_v1.service.MentorMyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MentorMyPageController {
    private MentorMyPageService mentorMyPageService;

    @Autowired
    public MentorMyPageController(MentorMyPageService mentorMyPageService){
        this.mentorMyPageService = mentorMyPageService;
        FindUserInfo.mentorMyPageService = mentorMyPageService;
    }

    @GetMapping("/mentor-mypage")
    public ResponseEntity<MentorProfileResponseDto> getMentorMyPage(){

        int mentorId = FindUserInfo.getCurrentMentorUserId();

        MentorProfileResponseDto dto = mentorMyPageService.getUserInfo(mentorId);

        return ResponseEntity.ok(dto);

    }







}
