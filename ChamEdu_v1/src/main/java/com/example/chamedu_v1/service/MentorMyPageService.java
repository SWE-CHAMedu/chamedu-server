package com.example.chamedu_v1.service;

import com.example.chamedu_v1.data.dto.*;
import com.example.chamedu_v1.data.entity.*;
import com.example.chamedu_v1.data.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Service
public class MentorMyPageService {

    public static final int MENTOR_INCOME = 100; // 건당 멘토 수익

    @Autowired
    private MentorRepository mentorRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MenteeRepository menteeRepository;

    // 상담신청 목록 조회
    public List<Room> receiveChatRequests(String userId) {
        return roomRepository.findAllByMentor_UserId(userId);
    }

    // 상담 수락 또는 거절 처리
    @Transactional
    public void answerChatRequests(ChatAnswerRequestDto chatAnswerRequestDto) {
        Room room = roomRepository.findByRoomId(chatAnswerRequestDto.getRoomId());
        if (chatAnswerRequestDto.isAnswer()){ room.setStatus('A'); }
        else { room.setStatus('D'); }
        roomRepository.save(room);
    }

    // 채팅 끝난 뒤처리
    @Transactional
    public void afterChatFinish(ChatCompleteDto chatCompleteDto){
        // 룸 상태 'C'로 변경
        Room room = roomRepository.findByRoomId(chatCompleteDto.getRoomId());
        room.setStatus('C');
        roomRepository.save(room);
        // 멘토 상담횟수 증가 및 포인트 부여
        Mentor mentor = mentorRepository.findByUserId(chatCompleteDto.getMentor_userId());
        mentor.setChatCount(mentor.getChatCount()+1);
        mentor.setPoint(mentor.getPoint()+MENTOR_INCOME);
        mentorRepository.save(mentor);
        // 채팅내역 기록?에도 넣어야하는지
    }

    public MentorProfileResponseDto getUserInfo(String userId) {
        Mentor mentorInfo = mentorRepository.findByUserId(userId);
        Profile profileInfo = profileRepository.findByMentor_UserId(userId);
        Room room = roomRepository.findFirstByMentor_UserIdOrderByStartDateDesc(userId);
        int reviewCount = reviewRepository.countByMentor_UserId(userId);
        float avgScore = reviewRepository.findAveragePointByMentorUserId(userId);
        int requestRoomCount = roomRepository.countByMentor_UserId(userId);

        MentorProfileResponseDto myPageDto = new MentorProfileResponseDto();

        if (room != null && room.getStartDate() != null) {
            LocalDateTime startDate = room.getStartDate().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDateTime();

            LocalDateTime currentDate = LocalDateTime.now();

            Duration duration = Duration.between(currentDate, startDate);

            long hours = duration.toHours();
            long minutes = duration.minusHours(hours).toMinutes();

            String currentChatTime = String.format("%d시간 %d분 후에 상담이 예정되어 있습니다.", hours, minutes);
            myPageDto.setCurrentChatTime(currentChatTime);

        }else{
            myPageDto.setCurrentChatTime("상담 예정 시간이 없습니다.");
        }

        myPageDto.setUserImg(profileInfo.getProfileImg());
        myPageDto.setNickname(mentorInfo.getNickname());
        myPageDto.setAdmissionType(profileInfo.getAdmissionType());
        myPageDto.setUniversity(profileInfo.getUniversity());
        myPageDto.setPromotionText(profileInfo.getPromotionText());
        myPageDto.setAvgScore(avgScore);
        myPageDto.setRequestRoomCount(requestRoomCount);
        myPageDto.setReviewCount(reviewCount);

        List<Review> reviewList = reviewRepository.findAllByMentor_UserId(userId);

        List<ReviewMyPageResponseDto> reviewDtoList = reviewList.stream()
                .map(review -> {
                    ReviewMyPageResponseDto reviewDto = new ReviewMyPageResponseDto();
                    reviewDto.setReviewId(review.getReviewId());
                    reviewDto.setTitle(review.getTitle());
                    reviewDto.setPoint(review.getScore());
                    reviewDto.setContent(review.getContent());
                    // 필요한 다른 정보들도 설정해야 합니다.
                    return reviewDto;
                })
                .collect(Collectors.toList());

        myPageDto.setReviewList(reviewDtoList);


        return myPageDto;
    }




    public Profile updateMentorProfile(String userId, MentorProfileUpdateRequestDto updateDto){
        Profile profileInfo = profileRepository.findByMentor_UserId(userId);
        Mentor mentorInfo = mentorRepository.findByUserId(userId);


        mentorInfo.setNickname(updateDto.getNickName());
        profileInfo.setProfileImg(updateDto.getUserImg());
        profileInfo.setUniversity(updateDto.getUniversity());
        profileInfo.setCollege(updateDto.getCollege());
        profileInfo.setAdmissionType(updateDto.getAdmissionType());
        profileInfo.setPromotionText(updateDto.getPromotionText());

        List<Time> convertedAvailableTime = updateDto.convertToTimeList();
        mentorInfo.setAvailableTime(convertedAvailableTime);


        profileRepository.save(profileInfo);
        mentorRepository.save(mentorInfo);
        
        return profileInfo;
    }

    public List<ChatHistoryResponseDto> chatMentorHistory(String userId) {
        LocalDateTime currentServerTime = LocalDateTime.now();
        List<Room> roomHistory = roomRepository.findAllByMentor_UserId(userId);
        Mentor mentorInfo = mentorRepository.findByUserId(userId);



        List<ChatHistoryResponseDto> roomDtoList = roomHistory.stream()
                .map(room -> {
                    ChatHistoryResponseDto dto = new ChatHistoryResponseDto();
                    Mentee mentee = menteeRepository.findByMenteeId(room.getMentee().getMenteeId());
                    dto.setUserName(mentee.getName());
                    dto.setRoomId(room.getRoomId());

                    LocalDateTime startDate = room.getStartDate().toInstant().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();
                    LocalDateTime endDate = room.getEndDate().toInstant().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();
                    dto.setStartTime(startDate);
                    dto.setEndTime(endDate);
                    dto.setTitle(room.getChatTitle());
                    // Check if the room has expired
                    LocalDateTime expired = room.getStartDate().toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDateTime();
                    Duration duration = Duration.between(expired, currentServerTime);
                    if(duration.toDays() > 30){
                        dto.setCheckStatus("만료됨");
                    }
                    else{
                        dto.setCheckStatus("채팅조회");
                    }



                    return dto;
                })
                .collect(Collectors.toList());



        return roomDtoList;
    }



}
