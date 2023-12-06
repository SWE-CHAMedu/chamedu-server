package com.example.chamedu_v1.service;

import com.example.chamedu_v1.data.dto.ReportRequestDto;
import com.example.chamedu_v1.data.entity.Report;
import com.example.chamedu_v1.data.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class ReportService {

    private final RoomRepository roomRepository;
    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(RoomRepository roomRepository, ReportRepository reportRepository) {
        this.reportRepository= reportRepository;
        this.roomRepository= roomRepository;
    }
    
    // 신고 데이터 생성
    public void uploadReport(ReportRequestDto reportRequestDto){
        String reporter=reportRequestDto.getUserId();
        int roomId=reportRequestDto.getRoomId();
        Report report = new Report();

        report.setCreateTime(LocalDateTime.now());
        report.setReportDetail(reporter+" : "+reportRequestDto.getReportDetail());
        report.setStatus("unprocessed");
        report.setReportType(1);
        report.setMentor(roomRepository.findMentorIdByRoomId(roomId));
        report.setMentee(roomRepository.findMenteeIdByRoomId(roomId));
        reportRepository.save(report); // 신고 엔티티 저장
    }

}
