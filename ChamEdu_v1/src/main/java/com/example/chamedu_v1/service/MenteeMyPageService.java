package com.example.chamedu_v1.service;

import com.example.chamedu_v1.data.dto.MenteeProfileResponseDto;
import com.example.chamedu_v1.data.dto.MenteeProfileUpdateDto;
import com.example.chamedu_v1.data.dto.ReviewMyPageResponseDto;
import com.example.chamedu_v1.data.dto.RoomMyPageResponseDto;
import com.example.chamedu_v1.data.entity.Mentee;
import com.example.chamedu_v1.data.entity.Review;
import com.example.chamedu_v1.data.entity.Room;
import com.example.chamedu_v1.data.repository.MenteeRepository;
import com.example.chamedu_v1.data.repository.RoomRepository;
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
public class MenteeMyPageService {

    @Autowired
    private MenteeRepository menteeRepository;

    @Autowired
    private RoomRepository roomRepository;

    public int findMenteeIdByUserId(String userId){
        Mentee mentee = menteeRepository.findByUserId(userId);
        if (mentee != null) {
            return mentee.getMenteeId();
        }
        return 0; // 해당 전화번호로 유저를 찾지 못한 경우


    }


    public MenteeProfileResponseDto getUserInfo(int menteeId){

        Mentee menteeInfo = menteeRepository.findByMenteeId(menteeId);
        Room sceduledroom = menteeRepository.findRoomStarDateByMenteeId(menteeId);
        int chatCount = menteeRepository.findRoomCountByMenteeId(menteeId);
        int reviewCount = menteeRepository.findReviewCountByMenteeId(menteeId);

        MenteeProfileResponseDto myPageDto = new MenteeProfileResponseDto();

        if (sceduledroom != null && sceduledroom.getStartDate() != null) {
            LocalDateTime startDate = sceduledroom.getStartDate().toInstant()
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

        myPageDto.setUserImg(menteeInfo.getProfileImg());
        myPageDto.setNickname(menteeInfo.getNickname());
        myPageDto.setWishCollege(menteeInfo.getWishCollege());
        myPageDto.setPromotionText(menteeInfo.getInfo());
        myPageDto.setPromotionText(menteeInfo.getInfo());
        myPageDto.setEndChatCount(chatCount);
        myPageDto.setReviewCount(reviewCount);

        List<Room> roomList = roomRepository.findAllByMenteeId(menteeId);

        List<RoomMyPageResponseDto> roomDtoList = roomList.stream()
                .map(room -> {
                    RoomMyPageResponseDto roomDto = new RoomMyPageResponseDto();
                    roomDto.setRoomId(room.getRoomId());
                    roomDto.setStartTime(room.getStartDate());
                    roomDto.setEndTime(room.getEndDate());
                    roomDto.setStatus(room.getStatus());
                    roomDto.setMentorName(room.getMentor().getNickname());
                    // 필요한 다른 정보들도 설정해야 합니다.
                    return roomDto;
                })
                .collect(Collectors.toList());

        myPageDto.setReqeustRoomList(roomDtoList);



        return myPageDto;


    }


    public Mentee updateMenteeProfile(int menteeId, MenteeProfileUpdateDto profileUpdateDto){
        Mentee menteeInfo = menteeRepository.findByMenteeId(menteeId);

        menteeInfo.setNickname(profileUpdateDto.getNickName());
        menteeInfo.setInfo(profileUpdateDto.getInfo());
        menteeInfo.setWishUniv(profileUpdateDto.getWishUniv());
        menteeInfo.setWishCollege(profileUpdateDto.getWishCollege());
        menteeInfo.setWishAdmissionType(profileUpdateDto.getWishAdmissionType());
        menteeInfo.setProfileImg(profileUpdateDto.getUserImg());

        menteeRepository.save(menteeInfo);

        return menteeInfo;
    }
}
