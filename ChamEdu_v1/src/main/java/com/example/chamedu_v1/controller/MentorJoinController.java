package com.example.chamedu_v1.controller;

import com.example.chamedu_v1.data.dto.MentorJoinRequestDto;
import com.example.chamedu_v1.data.entity.Mentor;
import com.example.chamedu_v1.service.MentorJoinService;
import com.example.chamedu_v1.service.MentorJoinServiceImpl;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class MentorJoinController {

    private final MentorJoinService mentorJoinService;

    @Autowired
    public MentorJoinController(MentorJoinService mentorJoinService) {
        this.mentorJoinService = mentorJoinService;
    }

    // 회원가입으로 이동
    @GetMapping("/join")
    public ResponseEntity<String> joinForm(){
        return ResponseEntity.ok("Join Form 페이지입니다");
    }

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody MentorJoinRequestDto joinRequestDto) {
        mentorJoinService.registerMentor(joinRequestDto);
        return ResponseEntity.ok("멘토가 성공적으로 회원가입이 완료되었습니다.");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Mentor mentor, HttpSession session) {
        if (mentorJoinService.authenticateUser(mentor)) {
            // 인증 성공 시 세션에 사용자 정보 저장
            session.setAttribute("userId", mentor.getUserId());
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Login failed");
        }
    }

    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        // 로그아웃 시 세션 제거
        session.removeAttribute("userId");
        return ResponseEntity.ok("Logout successful");
    }

}
