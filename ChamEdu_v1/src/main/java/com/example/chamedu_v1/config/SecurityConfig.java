package com.example.chamedu_v1.config;

import com.example.chamedu_v1.config.jwt.JwtAccessDeniedHandler;
import com.example.chamedu_v1.config.jwt.JwtAuthenticationEntryPoint;
import com.example.chamedu_v1.config.jwt.JwtFilter;

import com.example.chamedu_v1.service.MentorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MentorService mentorService;

    @Value("${jwt.secret}")
    private String secretKey;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 토큰방식은 csrf를 disable해야한다
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)

                // 로그인, 회원가입은 인증 없이 허용
                .authorizeRequests((authorizeRequests) ->
                        authorizeRequests
                    .requestMatchers("/auth/login", "/auth/join").permitAll()
                    .requestMatchers(HttpMethod.POST, "/**").authenticated()   // 나머지 API 는 전부 인증 필요
                )

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // .exceptionHandling()는 @EnableSecurity로 자동화되는 걸로 변경되어서 필요없어짐
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )

                .addFilterBefore(new JwtFilter(mentorService, secretKey), UsernamePasswordAuthenticationFilter.class);


        return httpSecurity.build();
    }

}