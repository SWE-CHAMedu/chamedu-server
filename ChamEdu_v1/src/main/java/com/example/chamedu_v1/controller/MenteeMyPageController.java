package com.example.chamedu_v1.controller;

import com.example.chamedu_v1.data.dto.ChatHistoryResponseDto;
import com.example.chamedu_v1.data.dto.MenteeProfileResponseDto;
import com.example.chamedu_v1.data.dto.MenteeProfileUpdateDto;
import com.example.chamedu_v1.data.dto.MentorProfileResponseDto;
import com.example.chamedu_v1.data.entity.Mentee;
import com.example.chamedu_v1.data.entity.Mentor;
import com.example.chamedu_v1.service.MenteeMyPageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class MenteeMyPageController {
    private MenteeMyPageService menteeMyPageService;


    public MenteeMyPageController(MenteeMyPageService menteeMyPageService){
        this.menteeMyPageService = menteeMyPageService;
    }

    @GetMapping("/mentee-mypage")
    public ResponseEntity<MenteeProfileResponseDto> getMenteeMyPage(HttpServletRequest request){

        HttpSession session = request.getSession();

        String userId = (String)session.getAttribute("userId");

        MenteeProfileResponseDto dto = menteeMyPageService.getUserInfo(userId);

        return ResponseEntity.ok(dto);

    }

    @PutMapping("/mentee-mypage/profile/update")
    public ResponseEntity<String> updateMenteeProfile(HttpServletRequest request,@RequestBody MenteeProfileUpdateDto profileUpdateDto){

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
    public ResponseEntity<String> deleteReview(@PathVariable int review_id, HttpServletRequest request){
        HttpSession session = request.getSession(); // 세션이 존재하지 않으면 null 반환
        String menteeUserId = (String)session.getAttribute("userId");
        if (menteeUserId != null) {
            return ResponseEntity.ok(menteeMyPageService.deleteReview(review_id));
        }
        return ResponseEntity.ok("fail");
    }


}
