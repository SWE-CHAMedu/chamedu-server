package com.example.chamedu_v1.config;

import com.example.chamedu_v1.config.jwt.JwtAccessDeniedHandler;
import com.example.chamedu_v1.config.jwt.JwtAuthenticationEntryPoint;
import com.example.chamedu_v1.config.jwt.JwtSecurityConfig;
import com.example.chamedu_v1.config.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable) // token방식은 csrf를 disable해야한다

                // jwt 토큰 방식으로, 세션을 사용하지 않기 때문에 STATELESS로 설정
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // .exceptionHandling() 이거 @EnableSecurity로 자동회되는 걸로 변경되어서 필요없어짐.
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )

                // enable h2-console
                //.headers()
                //.frameOptions()
                //.sameOrigin()

                // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정하겠다.
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                //.requestMatchers("/**").permitAll()
                                .requestMatchers("/api/signup").permitAll() // 회원가입 api
                                .requestMatchers("/api/login").permitAll() // 로그인 api
                                .requestMatchers("/test").permitAll() // test API
                                //.anyRequest().authenticated() // 그 외 인증 없이 접근X
                )


                .apply(new JwtSecurityConfig(tokenProvider)); // JwtFilter를 addFilterBefore로 등록했던 JwtSecurityConfig class 적용
        return httpSecurity.build();
    }

}