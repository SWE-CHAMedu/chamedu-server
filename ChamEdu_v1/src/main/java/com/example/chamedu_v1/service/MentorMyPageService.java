package com.example.chamedu_v1.service;

import com.example.chamedu_v1.data.dto.ChatHistoryResponseDto;
import com.example.chamedu_v1.data.dto.MentorProfileResponseDto;
import com.example.chamedu_v1.data.dto.MentorProfileUpdateRequestDto;
import com.example.chamedu_v1.data.dto.ReviewMyPageResponseDto;
import com.example.chamedu_v1.data.entity.Mentor;
import com.example.chamedu_v1.data.entity.Profile;
import com.example.chamedu_v1.data.entity.Review;
import com.example.chamedu_v1.data.entity.Room;
import com.example.chamedu_v1.data.repository.MentorRepository;
import com.example.chamedu_v1.data.repository.ProfileRepository;
import com.example.chamedu_v1.data.repository.ReviewRepository;
import com.example.chamedu_v1.data.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Service
public class MentorMyPageService {

    @Autowired
    private MentorRepository mentorRepository;
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RoomRepository roomRepository;



    @Transactional(readOnly = true)
    public int findMentorIdByUserId(String userId){
        Mentor mentor = mentorRepository.findByUserId(userId);
        if (mentor != null) {
            return mentor.getMentorId();
        }
        return 0; // 해당 전화번호로 유저를 찾지 못한 경우


    }

    public MentorProfileResponseDto getUserInfo(int mentorId) {
        Mentor mentorInfo = mentorRepository.findByMentorId(mentorId);
        Profile profileInfo = profileRepository.findByMentor(mentorId);
        Room room = profileRepository.findByStartDate(mentorId);
        int reviewCount = mentorRepository.findReviewCountByMentorId(mentorId);

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
        myPageDto.setUniversity(mentorInfo.getUniversity());
        myPageDto.setPromotionText(profileInfo.getPromotionText());
        myPageDto.setReviewCount(reviewCount);

        List<Review> reviewList = reviewRepository.findAllByMentor(mentorId);

        List<ReviewMyPageResponseDto> reviewDtoList = reviewList.stream()
                .map(review -> {
                    ReviewMyPageResponseDto reviewDto = new ReviewMyPageResponseDto();
                    reviewDto.setReviewId(review.getReviewId());
                    reviewDto.setTitle(review.getTitle());
                    reviewDto.setPoint(review.getScore());
                    reviewDto.setContent(reviewDto.getContent());
                    // 필요한 다른 정보들도 설정해야 합니다.
                    return reviewDto;
                })
                .collect(Collectors.toList());

        myPageDto.setReviewList(reviewDtoList);


        return myPageDto;
    }




    public Profile updateMentorProfile(int mentorId, MentorProfileUpdateRequestDto updateDto){
        Profile profileInfo = profileRepository.findByMentor(mentorId);
        Mentor mentorInfo = mentorRepository.findByMentorId(mentorId);



        mentorInfo.setNickname(updateDto.getNickName());
        profileInfo.setProfileImg(updateDto.getUserImg());
        profileInfo.setUniversity(updateDto.getUniversity());
        profileInfo.setCollege(updateDto.getCollege());
        profileInfo.setAdmissionType(profileInfo.getAdmissionType());
        profileInfo.setPromotionText(profileInfo.getPromotionText());
        //가능시간 추가

        List<Time> convertedAvailableTime = updateDto.convertToTimeList();
        mentorInfo.setAvailableTime(convertedAvailableTime);


        profileRepository.save(profileInfo);
        mentorRepository.save(mentorInfo);
        
        return profileInfo;
    }


}
