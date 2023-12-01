package com.example.chamedu_v1.controller;

import com.example.chamedu_v1.data.dto.MentorJoinRequestDto;
import com.example.chamedu_v1.data.dto.MenteeJoinRequestDto;
import com.example.chamedu_v1.data.entity.Mentee;
import com.example.chamedu_v1.data.entity.Mentor;
import com.example.chamedu_v1.service.MentorAccessService;
import com.example.chamedu_v1.service.MenteeAccessService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class UserController {

    private final MentorAccessService mentorAccessService;
    private final MenteeAccessService menteeAccessService;

    @Autowired
    public UserController(MentorAccessService mentorAccessService, MenteeAccessService menteeAccessService) {
        this.mentorAccessService = mentorAccessService;
        this.menteeAccessService = menteeAccessService;
    }

    // 회원가입 페이지로 이동
    @GetMapping("/join")
    public ResponseEntity<String> joinForm(){
        return ResponseEntity.ok("Join Form 페이지입니다");
    }

    // 회원가입 - 멘토
    @PostMapping("/join/mentor")
    public ResponseEntity<String> join(@RequestBody MentorJoinRequestDto joinRequestDto) {
        mentorAccessService.registerMentor(joinRequestDto);
        return ResponseEntity.ok("멘토가 성공적으로 회원가입이 완료되었습니다.");
    }

    // 회원가입 - 멘티
    @PostMapping("/join/mentee")
    public ResponseEntity<String> join(@RequestBody MenteeJoinRequestDto joinRequestDto) {
        menteeAccessService.registerMentee(joinRequestDto);
        return ResponseEntity.ok("멘티가 성공적으로 회원가입이 완료되었습니다.");
    }

    // 로그인 - 멘토
    @PostMapping("/login/mentor")
    public ResponseEntity<String> login(@RequestBody Mentor mentor, HttpSession session) {
        if (mentorAccessService.authenticateUser(mentor)) {
            // 인증 성공 시 세션에 사용자 정보 저장
            session.setAttribute("userId", mentor.getUserId());
            return ResponseEntity.ok("Mentor Login successful");
        } else {
            return ResponseEntity.status(401).body("Mentor Login failed");
        }
    }

    // 로그인 - 멘티
    @PostMapping("/login/mentee")
    public ResponseEntity<String> login(@RequestBody Mentee mentee, HttpSession session) {
        if (menteeAccessService.authenticateUser(mentee)) {
            // 인증 성공 시 세션에 사용자 정보 저장
            session.setAttribute("userId", mentee.getUserId());
            return ResponseEntity.ok("Mentee Login successful");
        } else {
            return ResponseEntity.status(401).body("Mentee Login failed");
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
