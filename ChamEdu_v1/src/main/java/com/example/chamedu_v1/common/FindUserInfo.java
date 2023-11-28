package com.example.chamedu_v1.common;

import com.example.chamedu_v1.service.MenteeMyPageService;
import com.example.chamedu_v1.service.MentorMyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FindUserInfo{

    public static MentorMyPageService mentorMyPageService;
    public static MenteeMyPageService menteeMyPageService;

    public static void setMentorMyPageService (MentorMyPageService mentorMyPageService, MenteeMyPageService menteeMyPageService){
        FindUserInfo.mentorMyPageService = mentorMyPageService;
        FindUserInfo.menteeMyPageService = menteeMyPageService;
    }

    public static int getCurrentMentorUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication != null) && (authentication.getPrincipal() instanceof UserDetails)) {
            String userId = authentication.getName();
            int mentorId = mentorMyPageService.findMentorIdByUserId(userId);
            return mentorId;
        }
        return 0; // 인증된 사용자가 없을 경우 null 반환
    }

    public static int getCurrentMenteeUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication != null) && (authentication.getPrincipal() instanceof UserDetails)) {
            String userId = authentication.getName();
            int menteeId = menteeMyPageService.findMenteeIdByUserId(userId);
            return menteeId;
        }
        return 0; // 인증된 사용자가 없을 경우 null 반환

    }
}



