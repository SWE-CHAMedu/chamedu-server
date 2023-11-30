package com.example.chamedu_v1.service;

import com.example.chamedu_v1.config.jwt.TokenProvider;
import com.example.chamedu_v1.data.entity.Mentor;
import com.example.chamedu_v1.data.repository.MentorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

//멘토의 회원가입과 로그인에 필요한 로직을 구현
@Service
@RequiredArgsConstructor
public class MentorService {

    private final MentorRepository mentorRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    private Long expiredMs = 1000 * 60 * 60L;
    public String login(String userId, String password) {
        // 인증과정 생략

        return TokenProvider.createJwt(userId, secretKey, expiredMs);
    }

    public String join(String userId, String password){

        // 로그인 ID 중복 check
        mentorRepository.findByUserId(userId); // 리턴을 option<mentor>타입으로 바꾸고싶다

        // 저장
        Mentor mentor = Mentor.builder()
                .userId(userId)
                .password(password)
                .build();
        mentorRepository.save(mentor);


        return "SUCCESS";
    }

}
