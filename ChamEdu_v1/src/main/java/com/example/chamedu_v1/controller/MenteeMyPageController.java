package com.example.chamedu_v1.controller;

import com.example.chamedu_v1.common.FindUserInfo;
import com.example.chamedu_v1.data.dto.MenteeProfileResponseDto;
import com.example.chamedu_v1.data.dto.MentorProfileResponseDto;
import com.example.chamedu_v1.service.MenteeMyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenteeMyPageController {
    private MenteeMyPageService menteeMyPageService;


    public MenteeMyPageController(MenteeMyPageService menteeMyPageService){
        this.menteeMyPageService = menteeMyPageService;
        FindUserInfo.menteeMyPageService = menteeMyPageService;
    }

    @GetMapping("/mentee-mypage")
    public ResponseEntity<MenteeProfileResponseDto> getMentorMyPage(){

        int mentorId = FindUserInfo.getCurrentMenteeUserId();

        MenteeProfileResponseDto dto = menteeMyPageService.getUserInfo(mentorId);

        return ResponseEntity.ok(dto);

    }



}
