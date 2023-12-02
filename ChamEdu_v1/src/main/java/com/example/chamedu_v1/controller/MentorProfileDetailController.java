package com.example.chamedu_v1.controller;

import com.example.chamedu_v1.data.dto.*;

import com.example.chamedu_v1.service.MentorProfileDetailService;
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
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class MentorProfileDetailController {
    @Autowired
    private MentorProfileDetailService mentorProfileDetailService;

    @Autowired
    private MentorProfileListService mentorProfileListService;

    @GetMapping("/mentor-profile/{mentorId}")
    @ResponseBody
    public ResponseEntity<MentorProfileDetailDto> getMentor(@PathVariable int mentorId){
//로그인 권한 필요 없을듯
//        HttpSession session = request.getSession();
//        String userId = (String)session.getAttribute("userId");
        MentorProfileDetailDto mentorDto;

        mentorDto=mentorProfileDetailService.getMentorProfileDetail(mentorId);
        return ResponseEntity.ok(mentorDto);
    }
//    @GetMapping("/mentor-profile/{request}")
//    @ResponseBody
//    public ResponseEntity<AvailableTimeDto> getAvailableTimeList(@PathVariable List<Time> availableTime) throws Exception{
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Page<MentorProfileDetailDto> MentorProfileList = mentorProfileListService.getMentorProfileList(modifiedPageable, authentication);
//        return ResponseEntity.ok(MentorProfileList);
//    }
//
//    @MentorMapping("/mentor-profile/{request}")
//    @ResponseBody
//    public ResponseEntity<Page<MentorProfileDetailDto>> getMentorList(@PageableDefault Pageable pageable) throws Exception{
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Pageable modifiedPageable = PageRequest.of(pageable.getPageNumber(), defaultPageSize);
//        Page<MentorProfileDetailDto> MentorProfileList = mentorProfileListService.getMentorProfileList(modifiedPageable, authentication);
//        return ResponseEntity.ok(MentorProfileList);
//    }
}
