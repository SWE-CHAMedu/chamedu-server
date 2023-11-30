package com.example.chamedu_v1.config.jwt;

 //실제로 이 컴포넌트를 이용하는 것은 인증 작업을 진행하는 Filter
// 이 필터는 검증이 끝난 JWT로부터 유저정보를 받아와서 UsernamePasswordAuthenticationFilter 로 전달

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;



@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final TokenProvider tokenProvider;

    // 실제 필터링 로직을 수행하는 곳..
    // 토큰의 인증정보를 SecurityContext에 저장하는 역할 수행
    @Override
    public void doFilterInternal(HttpServletRequest  servletRequest,
                         HttpServletResponse  servletResponse,
                         FilterChain filterChain) throws IOException, ServletException, java.io.IOException {

        // 1. Request Header 에서 토큰을 꺼냄
        String jwt = resolveToken(servletRequest);
        // String requestURI = servletRequest.getRequestURI();

        // 2. validateToken 으로 토큰 유효성 검사
        // 정상 토큰이면 해당 토큰으로 Authentication 을 가져와서 SecurityContext 에 저장
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), servletRequest.getRequestURI());
        } else {
            logger.debug("유효한 JWT 토큰이 없습니다, uri: {}", servletRequest.getRequestURI());
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    // Request Header 에서 토큰 정보를 꺼내오기 위한 메소드
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.split(" ")[1].trim();
        }
        return null;
    }
}