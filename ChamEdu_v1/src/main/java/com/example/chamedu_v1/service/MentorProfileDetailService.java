package com.example.chamedu_v1.service;

import com.example.chamedu_v1.data.dto.ChatRequestDto;
import com.example.chamedu_v1.data.dto.RoomDto;
import com.example.chamedu_v1.data.dto.MentorProfileDetailDto;
import com.example.chamedu_v1.data.entity.*;
import com.example.chamedu_v1.data.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class MentorProfileDetailService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private MenteeRepository menteeRepository;

    @Autowired
    private RoomRepository roomRepository;



    public float calculateAverageStarsByMentorId(int mentorId) {
        List<Review> reviews = reviewRepository.findReviewsByMentorMentorId(mentorId);
        if (reviews.isEmpty()) {
            return 0.0f; // 리뷰가 없는 경우 기본값 반환
        }
        float sum = 0.0f;
        for (Review review : reviews) {
            sum += review.getScore(); // 리뷰에서 별점 추출
        }
        return sum / reviews.size(); // 별점의 평균 반환
    }

    @Transactional
    public MentorProfileDetailDto getMentorProfileDetail(int mentorId){
        Profile profile = profileRepository.findByMentorMentorId(mentorId);
        float avgScore = calculateAverageStarsByMentorId(mentorId);
        profile.setAvgScore(avgScore);
        return new MentorProfileDetailDto(profile);
    }

    @Transactional
    public List<Time> getAvailableTimeList(int mentorId){
        List<Time> availableTimes = mentorRepository.findAvailableTimeByMentorId(mentorId);
        return availableTimes;
    }

    public Date stringToDateConversion(String stringDate){
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date converted = date.parse(stringDate);
            return converted;
        }catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Date calculateEndDate(Date startDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.MINUTE, 30);

        Date endDate = calendar.getTime();
        return endDate;
    }

    @Transactional
    public RoomDto createChatRequest(int mentorId, String menteeUserId, ChatRequestDto chatRequestDto){
        Mentee mentee = menteeRepository.findByUserId(menteeUserId);
        Mentor mentor = mentorRepository.findByMentorId(mentorId);

        String wishChatSchedule=chatRequestDto.getWishChatSchedule();
        String chatTitle=chatRequestDto.getChatTitle();

        Date starDate=stringToDateConversion(wishChatSchedule);
        Date endDate=calculateEndDate(starDate);

        Room wishChatRoom = new Room();
        wishChatRoom.setMentee(mentee);
        wishChatRoom.setMentor(mentor);

        wishChatRoom.setStartDate(starDate);
        wishChatRoom.setEndDate(endDate);

        wishChatRoom.setStatus('W');
        wishChatRoom.setChatTitle(chatTitle);

        Room saveRoom= roomRepository.save(wishChatRoom);

        return new RoomDto(saveRoom);
    }



}
