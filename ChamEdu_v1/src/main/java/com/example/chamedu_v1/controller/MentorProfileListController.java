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


    @GetMapping("/mentor-profile-list")
    public ResponseEntity<Page<MentorProfileDto>> getProfileList(HttpServletRequest request, Pageable pageable) throws Exception{
        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userId");
        //Pageable modifiedPageable = PageRequest.of(pageable.getPageNumber(), defaultPageSize);
        Page<MentorProfileDto> profileList = mentorProfileListService.getProfileList(pageable);
        return ResponseEntity.ok(profileList);
    }
}
