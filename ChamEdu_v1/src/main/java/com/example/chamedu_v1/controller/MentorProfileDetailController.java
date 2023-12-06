package com.example.chamedu_v1.controller;

import com.example.chamedu_v1.data.dto.*;

import com.example.chamedu_v1.data.repository.ProfileRepository;
import com.example.chamedu_v1.service.MentorProfileDetailService;
import com.example.chamedu_v1.service.MentorProfileListService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;

@CrossOrigin
@RestController
public class MentorProfileDetailController {
    private final MentorProfileDetailService mentorProfileDetailService;
    @Autowired
    public MentorProfileDetailController(MentorProfileDetailService mentorProfileDetailService, ProfileRepository profileRepository) {
        this.mentorProfileDetailService = mentorProfileDetailService;
    }

    /**
     * 멘토 프로필 조회
     */
    @GetMapping("/mentor-profile/{mentorId}")
    @ResponseBody
    public ResponseEntity<MentorProfileDetailDto> getMentor(@PathVariable int mentorId){

        MentorProfileDetailDto mentorDto;

        mentorDto=mentorProfileDetailService.getMentorProfileDetail(mentorId);
        return ResponseEntity.ok(mentorDto);
    }
    /**
     * 멘토 상담신청 팝업 - 조회
     */
    @GetMapping("/mentor-profile/request/{mentorId}")
    @ResponseBody
    public ResponseEntity<List<Time>> getAvailableTimeList(@PathVariable int mentorId) throws Exception{

        List<Time> availableTimeList = mentorProfileDetailService.getAvailableTimeList(mentorId);
        return ResponseEntity.ok(availableTimeList);
    }

    /**
     * 멘토 상담신청 팝업 - 신청
     */

    @PostMapping("/mentor-profile/request/{mentorId}")
    public ResponseEntity<?> requestChat (@RequestBody ChatRequestDto chatRequestDto, @PathVariable int mentorId, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(); // 세션이 존재하지 않으면 null 반환
        String menteeUserId = (String)session.getAttribute("userId");

        if (menteeUserId != null) {
            return ResponseEntity.ok(mentorProfileDetailService.createChatRequest(mentorId, menteeUserId, chatRequestDto));
        } else {
            // 세션이 존재하지 않을 때 로그인이 필요
            return ResponseEntity.status(500).body("로그인이 필요합니다.");
        }
    }
    /**
     * 멘토 리뷰 작성
     */
    @PostMapping("/mentor-profile/review/{mentorId}")
    public ResponseEntity<?> createReview (@RequestBody ReviewRequestDto reviewDto, @PathVariable int mentorId, HttpServletRequest request) throws Exception{
        HttpSession session = request.getSession(); // 세션이 존재하지 않으면 null 반환
        String menteeUserId = (String)session.getAttribute("userId");
        if (menteeUserId != null){
            mentorProfileDetailService.createReview(mentorId,menteeUserId,reviewDto);
            return ResponseEntity.ok("리뷰가 작성되었습니다.");

        }else{
            // 세션이 존재하지 않을 때 로그인이 필요
            return ResponseEntity.status(500).body("로그인이 필요합니다.");
        }
    }
}
