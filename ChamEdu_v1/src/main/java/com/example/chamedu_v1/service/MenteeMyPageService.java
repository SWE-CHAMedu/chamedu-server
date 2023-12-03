package com.example.chamedu_v1.service;

import com.example.chamedu_v1.data.dto.*;
import com.example.chamedu_v1.data.entity.Mentee;
import com.example.chamedu_v1.data.entity.Mentor;
import com.example.chamedu_v1.data.entity.Review;
import com.example.chamedu_v1.data.entity.Room;
import com.example.chamedu_v1.data.repository.MenteeRepository;
import com.example.chamedu_v1.data.repository.MentorRepository;
import com.example.chamedu_v1.data.repository.ReviewRepository;
import com.example.chamedu_v1.data.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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



    public MenteeProfileResponseDto getUserInfo(String userId){

        Mentee menteeInfo = menteeRepository.findByUserId(userId);
        Room sceduledroom = roomRepository.findFirstByMentee_UserIdOrderByStartDateDesc(userId);
        int chatCount = roomRepository.countByMentee_UserId(userId);
        int reviewCount = reviewRepository.countByMentee_UserId(userId);

        MenteeProfileResponseDto myPageDto = new MenteeProfileResponseDto();
        LocalDateTime currentDate = LocalDateTime.now();


        LocalDateTime startDate = sceduledroom.getStartDate().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
        log.info("currentDate={}",currentDate);
        log.info("scheduledRoom={}", startDate);
        if (sceduledroom.getStartDate() ==null || startDate.isBefore(currentDate)) {
            myPageDto.setCurrentChatTime("상담 예정 시간이 없습니다.");

        }else if(sceduledroom != null && sceduledroom.getStartDate() != null){
            Duration duration = Duration.between(currentDate, startDate);

            long hours = duration.toHours();
            long minutes = duration.minusHours(hours).toMinutes();

            String currentChatTime = String.format("%d시간 %d분 후에 상담이 예정되어 있습니다.", hours, minutes);
            myPageDto.setCurrentChatTime(currentChatTime);
        }

        myPageDto.setUserImg(menteeInfo.getProfileImg());
        myPageDto.setNickname(menteeInfo.getNickname());
        myPageDto.setWishCollege(menteeInfo.getWishCollege());
        myPageDto.setPromotionText(menteeInfo.getInfo());
        myPageDto.setPromotionText(menteeInfo.getInfo());
        myPageDto.setEndChatCount(chatCount);
        myPageDto.setReviewCount(reviewCount);

        List<Room> roomList = roomRepository.findAllByMentee_UserId(userId);

        List<RoomMyPageResponseDto> roomDtoList = roomList.stream()
                .map(room -> {
                    RoomMyPageResponseDto roomDto = new RoomMyPageResponseDto();
                    roomDto.setRoomId(room.getRoomId());
                    LocalDateTime roomStartDate = room.getStartDate().toInstant().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();
                    LocalDateTime roomEndDate = room.getEndDate().toInstant().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();

                    roomDto.setStartTime(roomStartDate);
                    roomDto.setEndTime(roomEndDate);
                    roomDto.setStatus(room.getStatus());
                    roomDto.setTitle(roomDto.getTitle());
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
        menteeInfo.setProfileImg(profileUpdateDto.getUserImg());

        menteeRepository.save(menteeInfo);

        return menteeInfo;
    }

    public List<ChatHistoryResponseDto> chatMenteeHistory(String userId) {
        LocalDateTime currentServerTime = LocalDateTime.now();
        List<Room> roomHistory = roomRepository.findAllByMentee_UserId(userId);



        List<ChatHistoryResponseDto> roomDtoList = roomHistory.stream()
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


}
