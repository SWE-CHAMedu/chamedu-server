package com.example.chamedu_v1.config.jwt;

import com.example.chamedu_v1.service.MentorService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final MentorService mentorService;
    private final String secretKey;

    // 권한 부여
    @Override
    protected void doFilterInternal(HttpServletRequest  request, HttpServletResponse  response,
                         FilterChain filterChain) throws ServletException, IOException {

        // 헤더에서 authorization 꺼내기
        final String authorization=request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorization : {}", authorization);

        // token을 안보내면 block
        if (authorization==null || !authorization.startsWith("Bearer ")){
            log.error("authorization이 없거나 잘못보냈습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        // Token 꺼내기
        String token=authorization.split(" ")[1];

        // Token이 Expired 되었는지 여부
        if (TokenProvider.isExpired(token, secretKey)){
            log.error("토큰이 만료되었습니다");
            filterChain.doFilter(request, response);
            return;
        }

        // UserId를 Token에서 꺼내기
        String userId="";

        // 권한을 부여
        UsernamePasswordAuthenticationToken authenticationToken=
                new UsernamePasswordAuthenticationToken(userId, null, List.of(new SimpleGrantedAuthority("USER")));

        // Detail을 넣어줌
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }


}