package com.example.chamedu_v1.controller;

import com.example.chamedu_v1.data.dto.*;
import com.example.chamedu_v1.data.repository.ProfileRepository;
import com.example.chamedu_v1.service.MentorProfileListService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
//    @GetMapping("/recommend-mentor-profile-list")
//    public ResponseEntity<MentorProfileListDto> getRecommendProfileList(HttpServletRequest request) throws Exception {
//
//        HttpSession session = request.getSession();
//        String userId = (String)session.getAttribute("userId");
//        MentorProfileListDto recommendProfileList = mentorProfileListService.getRecommendProfileList(userId);
//        return ResponseEntity.ok(recommendProfileList);

//    }
    /**
     * 추천 멘토 둘러보기 - 로그인 시에만 사용가능, 로그인을 하지 않았다면 로그인하라는 메시지를 띄운다.
     */
@GetMapping("/recommend-mentor-profile-list")
public ResponseEntity<?> getRecommendProfileList(HttpServletRequest request) throws Exception {
    HttpSession session = request.getSession(); // 세션이 존재하지 않으면 null 반환
    if (session != null) {
        String userId = (String)session.getAttribute("userId");
        if (userId != null) {
            MentorProfileListDto recommendProfileList = mentorProfileListService.getRecommendProfileList(userId);
            return ResponseEntity.ok(recommendProfileList);
        } else {
            // userId가 null이라면 로그인이 필요합니다.
            return ResponseEntity.status(500).body("로그인이 필요합니다.");
        }
    } else {
        // 세션이 존재하지 않을 때도 로그인이 필요합니다.
        return ResponseEntity.status(500).body("로그인이 필요합니다.");
    }
}

}
