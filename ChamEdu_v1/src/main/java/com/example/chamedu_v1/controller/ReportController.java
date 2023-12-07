package com.example.chamedu_v1.controller;

import com.example.chamedu_v1.data.dto.MenteeJoinRequestDto;
import com.example.chamedu_v1.data.dto.MentorJoinRequestDto;
import com.example.chamedu_v1.data.dto.ReportRequestDto;
import com.example.chamedu_v1.data.entity.Mentor;
import com.example.chamedu_v1.service.MenteeAccessService;
import com.example.chamedu_v1.service.MentorAccessService;
import com.example.chamedu_v1.service.ReportService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report")
@CrossOrigin(origins = "*", methods = RequestMethod.GET)
public class ReportController {

    private final ReportService reportService;
    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // 신고페이지로 이동
    @GetMapping("/form")
    public ResponseEntity<String> reportForm(){
        return ResponseEntity.ok("신고 Form 페이지입니다");
    }

    // 신고하기
    @PostMapping("/upload")
    public ResponseEntity<String> uploadReport(@RequestBody ReportRequestDto reportRequestDto) {
        reportService.uploadReport(reportRequestDto);
        return ResponseEntity.ok("신고가 정상적으로 접수되었습니다. ");
    }

}
