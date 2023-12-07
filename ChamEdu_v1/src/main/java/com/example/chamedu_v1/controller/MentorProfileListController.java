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

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class MentorProfileListController {
    private final MentorProfileListService mentorProfileListService;
    private final ProfileRepository profileRepository;
    public static final String USER_ID = "userId";

    @Autowired
    public MentorProfileListController(MentorProfileListService mentorProfileListService, ProfileRepository profileRepository) {
        this.mentorProfileListService = mentorProfileListService;
        this.profileRepository = profileRepository;
    }

    /**
     * 멘토 둘러보기 - 최신순으로 멘토를 보여준다. 로그인하지 않아도 접속 가능
     */
    @GetMapping("/mentor-profile-list")
    public ResponseEntity<Page<MentorProfileDto>> getProfileList(HttpServletRequest request, Pageable pageable) {
        Page<MentorProfileDto> profileList = mentorProfileListService.getProfileList(pageable);
        return ResponseEntity.ok(profileList);
    }

    /**
     * 추천 멘토 둘러보기
     * 멘티만 사용가능
     */

    @GetMapping("/recommend-mentor-profile-list/{userId}")
    public ResponseEntity<MentorProfileListDto> getRecommendProfileList(@PathVariable String userId, HttpServletRequest request){

        MentorProfileListDto recommendProfileList = mentorProfileListService.getRecommendProfileList(userId);
        return ResponseEntity.ok(recommendProfileList);
    }
}
