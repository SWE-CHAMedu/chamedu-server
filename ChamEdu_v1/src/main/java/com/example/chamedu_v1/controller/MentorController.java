package com.example.chamedu_v1.controller;


import com.example.chamedu_v1.data.dto.MentorSignUpDto;
import com.example.chamedu_v1.data.entity.Mentor;
import com.example.chamedu_v1.data.repository.MentorRepository;
import com.example.chamedu_v1.service.MentorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor //이거쓸때는 반드시 final생성자.
@RequestMapping("/auth")
public class MentorController {

        private final MentorService mentorService;

        //로그인 페이지로 이동
        @GetMapping("/login") //화면매핑
        public String signinForm() {
            return "auth/login";
        }

        //회원가입 페이지로 이동
        @GetMapping("/join/mentor")  //
        public String signupForm() {
            return "auth/join/mentor";
        }

        //회원가입: 값을 저장하기 위해, 현재주소 그대로 매핑
        @PostMapping("/join/mentor")
        public ResponseEntity<Boolean> signUp(@RequestBody MentorSignUpDto mentorSignUpDto) {
            mentorService.join(mentorSignUpDto);
            return ResponseEntity.ok(true);
        }

        @GetMapping("/logout")
        public ResponseEntity<Boolean> logoutPage(HttpServletRequest request, HttpServletResponse response) {
            new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
            return ResponseEntity.ok(true);
        }
    }