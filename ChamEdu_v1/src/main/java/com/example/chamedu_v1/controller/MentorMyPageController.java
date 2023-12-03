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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import java.util.List;

@RestController
public class MentorMyPageController {
    public static final String USER_ID = "userId";
    private MentorMyPageService mentorMyPageService;

    @Autowired
    public MentorMyPageController(MentorMyPageService mentorMyPageService){
        this.mentorMyPageService = mentorMyPageService;
    }

    @GetMapping("/mentor-mypage")
    public ResponseEntity<MentorProfileResponseDto> getMentorMyPage(HttpServletRequest request){

        HttpSession session = request.getSession();

        String userId = (String)session.getAttribute("userId");

        MentorProfileResponseDto dto = mentorMyPageService.getUserInfo(userId);

        return ResponseEntity.ok(dto);
    }

    // 채팅 요청 목록 조회
    @GetMapping("/mentor-mypage/chat-request")
    public ResponseEntity<List<Integer>> check(HttpSession session) {
        String userId = (String)session.getAttribute(USER_ID);
        List<Integer> roomIdList = mentorMyPageService.receiveChatRequests(userId)
                .stream()
                .map(Room::getRoomId)
                .toList();
        return ResponseEntity.ok(roomIdList);
    }

    // 채팅 요청에 응답
    @PostMapping("/mentor-mypage/chat-request/answer")
    public ResponseEntity<String> answer( @RequestBody ChatAnswerRequestDto chatAnswerRequestDto) {
        mentorMyPageService.answerChatRequests(chatAnswerRequestDto);
        return ResponseEntity.ok("응답이 정상적으로 처리되었습니다.");
    }


    @PutMapping("/mentor-mypage/profile/update")
    public ResponseEntity<String> updateMentorProfile(HttpServletRequest request, @RequestBody MentorProfileUpdateRequestDto updateRequestDto){

        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute(USER_ID);
        Profile mentorInfo = mentorMyPageService.updateMentorProfile(userId, updateRequestDto);

        if(mentorInfo!=null){
            return ResponseEntity.ok("회원 정보 수정에 성공하였습니다.");
        }


        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 정보 수정에 실패하였습니다.");

    }

    @GetMapping("/mentor-mypage/chat-log")
    public ResponseEntity<List<ChatHistoryResponseDto>> MentorChatLogHistory(HttpServletRequest request){

        HttpSession session = request.getSession();

        String userId = (String)session.getAttribute("userId");

        List<ChatHistoryResponseDto> chatHistoryList = mentorMyPageService.chatMentorHistory(userId);

        return ResponseEntity.ok(chatHistoryList);
    }






}
