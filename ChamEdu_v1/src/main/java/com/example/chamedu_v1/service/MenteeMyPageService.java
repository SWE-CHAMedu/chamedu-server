package com.example.chamedu_v1.service;

import com.example.chamedu_v1.data.dto.*;
import com.example.chamedu_v1.data.entity.*;
import com.example.chamedu_v1.data.repository.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;



import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import java.util.stream.Collectors;

@Component
@Service
@Slf4j
public class MenteeMyPageService {

    @Autowired
    private MenteeRepository menteeRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private MenteeProfileImgRepository menteeProfileImgRepository;



    public MenteeProfileResponseDto getUserInfo(String userId){

        Mentee menteeInfo = menteeRepository.findByUserId(userId);
        Room scheduledroom = roomRepository.findFirstByMentee_UserIdOrderByStartDateDesc(userId);
        int chatCount = roomRepository.countByMentee_UserId(userId);
        int reviewCount = reviewRepository.countByMentee_UserId(userId);


        MenteeProfileResponseDto myPageDto = new MenteeProfileResponseDto();
        LocalDateTime currentDate = LocalDateTime.now();


        if (scheduledroom != null && scheduledroom.getStartDate() != null) {
            LocalDateTime startDateTime = scheduledroom.getStartDate().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDateTime();


            Duration duration = Duration.between(currentDate, startDateTime);

            long hours = duration.toHours();
            long minutes = duration.minusHours(hours).toMinutes();

            String currentChatTime = String.format("%d시간 %d분 후에 상담이 예정되어 있습니다.", hours, minutes);
            myPageDto.setCurrentChatTime(currentChatTime);

        }else{
            myPageDto.setCurrentChatTime("상담 예정 시간이 없습니다.");
        }

        myPageDto.setNickname(menteeInfo.getNickname());
        myPageDto.setWishCollege(menteeInfo.getWishCollege());
        myPageDto.setPromotionText(menteeInfo.getInfo());
        myPageDto.setPromotionText(menteeInfo.getInfo());
        myPageDto.setEndChatCount(chatCount);
        myPageDto.setReviewCount(reviewCount);

        List<Room> roomList = roomRepository.findAllByMentee_UserId(userId);

        List<RoomMyPageResponseDto> roomDtoList = roomList.stream()
                .filter(room -> !"C".equals(String.valueOf(room.getStatus())))
                .map(room -> {
                    RoomMyPageResponseDto roomDto = new RoomMyPageResponseDto();
                    roomDto.setRoomId(room.getRoomId());
                    LocalDateTime roomStartDate = room.getStartDate().toInstant().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();
                    LocalDateTime roomEndDate = room.getEndDate().toInstant().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();

                    roomDto.setStartTime(roomStartDate);
                    roomDto.setEndTime(roomEndDate);
                    roomDto.setStatus(room.getStatus());
                    roomDto.setTitle(room.getChatTitle());
                    roomDto.setMentorName(room.getMentor().getNickname());
                    // 필요한 다른 정보들도 설정해야 합니다.
                    return roomDto;
                })
                .collect(Collectors.toList());

        myPageDto.setReqeustRoomList(roomDtoList);
        return myPageDto;
    }


    public Mentee updateMenteeProfile(String userId, MenteeProfileUpdateDto profileUpdateDto){

        Mentee menteeInfo = menteeRepository.findByUserId(userId);




        menteeInfo.setNickname(profileUpdateDto.getNickName());
        menteeInfo.setInfo(profileUpdateDto.getInfo());
        menteeInfo.setWishUniv(profileUpdateDto.getWishUniv());
        menteeInfo.setWishCollege(profileUpdateDto.getWishCollege());
        menteeInfo.setWishAdmissionType(profileUpdateDto.getWishAdmissionType());


        menteeRepository.save(menteeInfo);


        return menteeInfo;
    }

    public List<ChatHistoryResponseDto> chatMenteeHistory(String userId) {
        LocalDateTime currentServerTime = LocalDateTime.now();
        List<Room> roomHistory = roomRepository.findAllByMentee_UserId(userId);

        List<ChatHistoryResponseDto> roomDtoList = roomHistory.stream()
                .filter(room -> "C".equals(String.valueOf(room.getStatus())))
                .map(room -> {
                    ChatHistoryResponseDto dto = new ChatHistoryResponseDto();
                    Mentor mentor = mentorRepository.findByMentorId(room.getMentor().getMentorId());
                    dto.setUserName(mentor.getName());
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


    @Transactional
    public String deleteReview(int reviewId) {
        reviewRepository.deleteById(reviewId);
        return "리뷰가 삭제되었습니다.";
    }


    public Page<ReviewDto> getReviewList(Pageable pageable, String menteeUserId) {

        int menteeId=menteeRepository.findByUserId(menteeUserId).getMenteeId();

        return reviewRepository.findByMenteeMenteeId(pageable,menteeId).map(ReviewDto::new);
    }
}
