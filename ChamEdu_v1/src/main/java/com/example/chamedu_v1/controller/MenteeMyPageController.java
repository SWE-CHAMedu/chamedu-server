package com.example.chamedu_v1.controller;

import com.example.chamedu_v1.common.FindUserInfo;
import com.example.chamedu_v1.data.dto.MenteeProfileResponseDto;
import com.example.chamedu_v1.data.dto.MenteeProfileUpdateDto;
import com.example.chamedu_v1.data.dto.MentorProfileResponseDto;
import com.example.chamedu_v1.data.entity.Mentee;
import com.example.chamedu_v1.data.entity.Mentor;
import com.example.chamedu_v1.service.MenteeMyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenteeMyPageController {
    private MenteeMyPageService menteeMyPageService;


    public MenteeMyPageController(MenteeMyPageService menteeMyPageService){
        this.menteeMyPageService = menteeMyPageService;
        FindUserInfo.menteeMyPageService = menteeMyPageService;
    }

    @GetMapping("/mentee-mypage")
    public ResponseEntity<MenteeProfileResponseDto> getMenteeMyPage(){

        int menteeId = FindUserInfo.getCurrentMenteeUserId();

        MenteeProfileResponseDto dto = menteeMyPageService.getUserInfo(menteeId);

        return ResponseEntity.ok(dto);

    }

    @PutMapping("/mentee-mypage/profile/update")
    public ResponseEntity<String> updateMenteeProfile(@RequestBody MenteeProfileUpdateDto profileUpdateDto){

        int menteeId = FindUserInfo.getCurrentMenteeUserId();

        Mentee menteeInfo = menteeMyPageService.updateMenteeProfile(menteeId,profileUpdateDto);

        if(menteeInfo!=null){
            return ResponseEntity.ok("회원 정보 수정에 성공하셨습니다!");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 정보 수정에 실패하였습니다.");
    }







}
