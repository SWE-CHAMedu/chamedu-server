package com.example.chamedu_v1.controller;

import com.example.chamedu_v1.data.dto.MentorJoinRequestDto;
import com.example.chamedu_v1.service.MentorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class MentorController {

        private final MentorService mentorService;

        // 토큰 발급 방식 로그인
        @GetMapping("/login")
        public ResponseEntity<String> login() {
            return ResponseEntity.ok().body(mentorService.login("",""));
        }

        // 회원가입 페이지로 이동
        @GetMapping("/join/mentor")
        public String joinForm() {
            return "auth/join/mentor";
        }

        // 회원가입
        @PostMapping("/join/mentor")
        public ResponseEntity<String> join(@RequestBody MentorJoinRequestDto dto) {
            return ResponseEntity.ok("회원가입에 성공했습니다.");
        }

        // 로그아웃
        @GetMapping("/logout")
        public ResponseEntity<Boolean> logoutPage(HttpServletRequest request, HttpServletResponse response) {
            new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
            return ResponseEntity.ok(true);
        }

        //
    }