package com.example.chamedu_v1.config.jwt;

import org.springframework.stereotype.Component;

/* 필요한 권한이 존재하지 않는 경우에 403 Forbidden 에러를 리턴하는 class  */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        //필요한 권한이 없이 접근하려 할때 403
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}