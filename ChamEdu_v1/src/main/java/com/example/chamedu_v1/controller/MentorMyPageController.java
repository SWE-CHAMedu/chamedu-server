package com.example.chamedu_v1.controller;

import com.example.chamedu_v1.data.dto.MentorProfileResponseDto;
import com.example.chamedu_v1.data.dto.MentorProfileUpdateRequestDto;
import com.example.chamedu_v1.data.entity.Mentee;
import com.example.chamedu_v1.data.entity.Mentor;
import com.example.chamedu_v1.data.entity.Profile;
import com.example.chamedu_v1.service.MentorMyPageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MentorMyPageController {
    private MentorMyPageService mentorMyPageService;

    @Autowired
    public MentorMyPageController(MentorMyPageService mentorMyPageService){
        this.mentorMyPageService = mentorMyPageService;
    }

    @GetMapping("/mentor-mypage")
    public ResponseEntity<MentorProfileResponseDto> getMentorMyPage(HttpServletRequest request){

        HttpSession session = request.getSession();

        String userId = (String)session.getAttribute("user_id");

        MentorProfileResponseDto dto = mentorMyPageService.getUserInfo(userId);

        return ResponseEntity.ok(dto);

    }

    @PutMapping("/mentor-mypage/profile/update")
    public ResponseEntity<String> updateMentorProfile(HttpServletRequest request, @RequestBody MentorProfileUpdateRequestDto updateRequestDto){

        HttpSession session = request.getSession();

        String userId = (String)session.getAttribute("user_id");

        Profile mentorInfo = mentorMyPageService.updateMentorProfile(userId, updateRequestDto);

        if(mentorInfo!=null){
            return ResponseEntity.ok("회원 정보 수정에 성공하였습니다.");
        }


        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 정보 수정에 실패하였습니다.");

    }






}
