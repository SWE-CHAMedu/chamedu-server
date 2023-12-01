package com.example.chamedu_v1.config;
import com.example.chamedu_v1.service.MentorJoinService;
import com.example.chamedu_v1.service.MentorJoinServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    
    // 패스워드 인코더 쓸거면 이곳에 주입
//    @Bean
//    public MentorJoinService mentorJoinService() {
//        return new MentorJoinServiceImpl();
//    }
}