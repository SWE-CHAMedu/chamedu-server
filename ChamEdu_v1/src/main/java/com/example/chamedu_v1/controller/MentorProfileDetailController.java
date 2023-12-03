package com.example.chamedu_v1.controller;

import com.example.chamedu_v1.data.dto.*;

import com.example.chamedu_v1.service.MentorProfileDetailService;
import com.example.chamedu_v1.service.MentorProfileListService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;

@CrossOrigin
@RestController
public class MentorProfileDetailController {
    @Autowired
    private MentorProfileDetailService mentorProfileDetailService;

    @Autowired
    private MentorProfileListService mentorProfileListService;

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

//    @PostMapping("/mentor-profile/request/{mentorId}")
//    public ResponseEntity<RoomDto> requestChat (@RequestBody ChatRequestDto chatRequestDto, @PathVariable int mentorId, HttpServletRequest request) throws Exception{
//        HttpSession session = request.getSession(); // 세션이 존재하지 않으면 null 반환
//        String userId = (String)session.getAttribute("userId");
//
//        return ResponseEntity.ok(mentorProfileDetailService.createChatRequest(mentorId,userId,chatRequestDto));
//    }

    /**
     * 멘토 리뷰 작성
     */
}
