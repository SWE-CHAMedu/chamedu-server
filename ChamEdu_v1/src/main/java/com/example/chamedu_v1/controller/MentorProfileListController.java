package com.example.chamedu_v1.controller;

import com.example.chamedu_v1.data.dto.*;
import com.example.chamedu_v1.data.repository.ProfileRepository;
import com.example.chamedu_v1.service.MentorProfileListService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.Role;

@CrossOrigin
@RestController
public class MentorProfileListController {
    private final MentorProfileListService mentorProfileListService;
    private final ProfileRepository profileRepository;

    @Autowired
    public MentorProfileListController(MentorProfileListService mentorProfileListService, ProfileRepository profileRepository) {
        this.mentorProfileListService = mentorProfileListService;
        this.profileRepository = profileRepository;
    }
//    private final int defaultPageSize = 4; //수정가능

    /**
     * 멘토 둘러보기 - 최신순으로 멘토를 보여준다. 로그인하지 않아도 접속 가능
     */
    @GetMapping("/mentor-profile-list")
    public ResponseEntity<Page<MentorProfileDto>> getProfileList(HttpServletRequest request, Pageable pageable) throws Exception{
        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userId");
        //Pageable modifiedPageable = PageRequest.of(pageable.getPageNumber(), defaultPageSize);
        Page<MentorProfileDto> profileList = mentorProfileListService.getProfileList(pageable);
        return ResponseEntity.ok(profileList);
    }

    /**
     * 추천 멘토 둘러보기 - 로그인 시에만 사용가능, 로그인을 하지 않았다면 로그인하라는 메시지를 띄운다.
     * 멘티만 사용가능하기에 멘토가 접근하면 멘티만 접근가능한 페이지라고 메시지를 띄운다.
     */
    @GetMapping("/recommend-mentor-profile-list")
    public ResponseEntity<?> getRecommendProfileList(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(); // 세션이 존재하지 않으면 null 반환
        if (session != null) {
            //controller처럼 USER_ID,ROLE로 설정하면 getAttribute하지 못함.
            String userId = (String) session.getAttribute("userId");
            String role = (String) session.getAttribute("role");

            if (userId != null) {
                if ("mentee".equals(role)) { // ROLE이 "mentee"인 경우에만 처리
                        MentorProfileListDto recommendProfileList = mentorProfileListService.getRecommendProfileList(userId);
                        return ResponseEntity.ok(recommendProfileList);
                } else {
                    return ResponseEntity.status(500).body("멘티만 접근 가능한 페이지입니다.");
                }
            } else {
                // userId가 null이라면 로그인이 필요
                return ResponseEntity.status(500).body("로그인이 필요합니다.");
            }
        } else {
            // 세션이 존재하지 않을 때도 로그인이 필요
            return ResponseEntity.status(500).body("로그인이 필요합니다.");
        }
    }


}
