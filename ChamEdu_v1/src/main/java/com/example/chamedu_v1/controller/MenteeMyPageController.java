package com.example.chamedu_v1.controller;

import com.example.chamedu_v1.data.dto.*;
import com.example.chamedu_v1.data.entity.Mentee;

import com.example.chamedu_v1.service.MenteeMyPageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class MenteeMyPageController {
    public static final String USER_ID = "userId";
    private MenteeMyPageService menteeMyPageService;


    public MenteeMyPageController(MenteeMyPageService menteeMyPageService){
        this.menteeMyPageService = menteeMyPageService;
    }

    @GetMapping("/mentee-mypage/{userId}")
    public ResponseEntity<MenteeProfileResponseDto> getMenteeMyPage(@PathVariable String userId, HttpServletRequest request){

        MenteeProfileResponseDto dto = menteeMyPageService.getUserInfo(userId);

        return ResponseEntity.ok(dto);

    }

    @PutMapping("/mentee-mypage/profile/update")
    public ResponseEntity<String> updateMenteeProfile(HttpServletRequest request, @RequestBody MenteeProfileUpdateDto profileUpdateDto){

        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userId");

        Mentee menteeInfo = menteeMyPageService.updateMenteeProfile(userId,profileUpdateDto);

        if(menteeInfo!=null){
            return ResponseEntity.ok("회원 정보 수정에 성공하셨습니다!");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 정보 수정에 실패하였습니다.");
    }

    @GetMapping("/mentee-mypage/chat-log")
    public ResponseEntity<List<ChatHistoryResponseDto>> MenteeChatLogHistory(HttpServletRequest request){

        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userId");

        List<ChatHistoryResponseDto> chatHistoryList = menteeMyPageService.chatMenteeHistory(userId);
        return ResponseEntity.ok(chatHistoryList);
    }

    @DeleteMapping("/review/{review_id}")
    public ResponseEntity<String> deleteReview(@PathVariable int review_id){

            return ResponseEntity.ok(menteeMyPageService.deleteReview(review_id));

    }
    @GetMapping("/mentee-mypage/review")
    public ResponseEntity<?> getReviewList(HttpSession session, Pageable pageable){
        String menteeUserId = (String)session.getAttribute(USER_ID);
        if (menteeUserId != null) {
            return ResponseEntity.ok(menteeMyPageService.getReviewList(pageable,menteeUserId));
        }else{
            return ResponseEntity.ok("로그인이 필요합니다");
        }
    }


}