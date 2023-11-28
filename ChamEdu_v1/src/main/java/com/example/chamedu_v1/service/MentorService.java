package com.example.chamedu_v1.service;
//멘토의 회원가입과 로그인에 필요한 로직을 구현

import com.example.chamedu_v1.config.jwt.TokenProvider;
import com.example.chamedu_v1.data.dto.MentorSignUpDto;
import com.example.chamedu_v1.data.entity.Mentor;
import com.example.chamedu_v1.data.repository.MentorRepository;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@EnableWebSecurity
@Service
public class MentorService {
    private final MentorRepository mentorRepository;
    private final PasswordEncoder passwordEncoder;


    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final Logger logger = LoggerFactory.getLogger(MentorService.class);

    public MentorService(MentorRepository mentorRepository, PasswordEncoder passwordEncoder,
                         AuthenticationManagerBuilder authenticationManagerBuilder,
                         TokenProvider tokenProvider) {
        this.mentorRepository = mentorRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenProvider = tokenProvider;
    }

    @Transactional
    public void signUp(MentorSignUpDto mentorSignUpDto) {
        // MentorSignUpDto에서 받아온 비밀번호를 인코딩
        String encodedPassword = passwordEncoder.encode(mentorSignUpDto.getPassword());

        // 인코딩된 비밀번호를 Mentor 엔터티에 설정
        mentorSignUpDto.setPassword(encodedPassword);
        
        // Mentor 엔티티로 변환하여 저장
        mentorRepository.save(mentorSignUpDto.toEntity(  ));

        // 저장된 비밀번호를 로그에 출력하여 확인
        logger.info("Encoded Password: {}", encodedPassword);
    }
}
