package com.example.chamedu_v1.service;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import com.example.chamedu_v1.data.dto.*;
import com.example.chamedu_v1.data.entity.*;
import com.example.chamedu_v1.data.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.util.*;
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
    @Autowired
    private MentorProfileImgRepository mentorProfileImgRepository;


    // 상담목록 조회 ('W'는 신청목록, 'A'는 예정목록)
    public List<ChatInfoDto> checkChatRequests(String userId, char status) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.KOREA);
        List<Room> rooms= roomRepository.findAllByMentor_UserIdAndStatus(userId, status);
        return rooms.stream()
                .map(room -> {
                    String dateString = dateFormat.format(room.getStartDate());
                    String startTimeString = timeFormat.format(room.getStartDate());
                    String endTimeString = timeFormat.format(room.getEndDate());
                    return new ChatInfoDto(
                            room.getRoomId(),
                            room.getMentee().getName(),
                            dateString,
                            startTimeString+"~"+endTimeString,
                            room.getChatTitle()
                    );
                })
                .toList();
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

        //myPageDto.setUserImg(profileInfo.getProfileImg());
        myPageDto.setNickname(mentorInfo.getNickname());
        myPageDto.setAdmissionType(profileInfo.getAdmissionType());
        myPageDto.setUniversity(profileInfo.getUniversity());
        myPageDto.setCollege(profileInfo.getCollege());
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

//    private String encodeImageToBase64(String fileName) {
//        try {
//            Path imagePath = Paths.get("src/main/images/" + fileName); // 이미지 파일 경로 지정
//            byte[] imageBytes = Files.readAllBytes(imagePath);
//            return Base64.getEncoder().encodeToString(imageBytes);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }




    public Profile updateMentorProfile(String userId, MentorProfileUpdateRequestDto updateDto, MultipartFile file){
        Profile profileInfo = profileRepository.findByMentor_UserId(userId);
        Mentor mentorInfo = mentorRepository.findByUserId(userId);
        MentorImageFile mentorImageFile = mentorProfileImgRepository.findByMentor_UserId(userId);

        String imageUrl = uploadProfileImage(file);

        if (mentorImageFile == null) {
            mentorImageFile = MentorImageFile.builder()
                    .origFileName(file.getOriginalFilename())
                    .filePath(imageUrl)
                    .fileSize(file.getSize())
                    .build();
        } else {
            // 이미지가 존재하는 경우 기존 이미지 업데이트
            mentorImageFile.setOrigFileName(file.getOriginalFilename());
            mentorImageFile.setFilePath(imageUrl);
            mentorImageFile.setFileSize(file.getSize());
        }



        mentorInfo.setNickname(updateDto.getNickName());
        profileInfo.setUniversity(updateDto.getUniversity());
        profileInfo.setCollege(updateDto.getCollege());
        profileInfo.setAdmissionType(updateDto.getAdmissionType());
        profileInfo.setPromotionText(updateDto.getPromotionText());

        List<Time> convertedAvailableTime = updateDto.convertToTimeList();
        mentorInfo.setAvailableTime(convertedAvailableTime);


        profileRepository.save(profileInfo);
        mentorRepository.save(mentorInfo);
        mentorProfileImgRepository.save(mentorImageFile);
        
        return profileInfo;
    }

    public List<ChatHistoryResponseDto> chatMentorHistory(String userId) {
        LocalDateTime currentServerTime = LocalDateTime.now();
        List<Room> roomHistory = roomRepository.findAllByMentor_UserId(userId);
        Mentor mentorInfo = mentorRepository.findByUserId(userId);



        List<ChatHistoryResponseDto> roomDtoList = roomHistory.stream()
                .filter(room -> "C".equals(String.valueOf(room.getStatus())))
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


    @Value("${upload.path}") // application.properties 또는 application.yml에 파일 업로드 경로를 설정해두어야 합니다.
    private String uploadPath;
    public String uploadProfileImage(MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {
                // 파일 저장 디렉토리 생성
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // 파일 이름 생성 (유니크한 이름을 사용하기 위해 UUID 사용)
                String fileName = UUID.randomUUID().toString() + "_" + Objects.requireNonNull(file.getOriginalFilename());

                // 파일 저장 경로 설정
                Path filePath = Paths.get(uploadPath, fileName);

                // 파일 저장
                file.transferTo(filePath);

                // 파일 URL 반환 (실제 프로덕션 환경에서는 CDN 등을 사용하여 별도로 관리하는 것이 좋음)
                return "/uploads/" + fileName; // 예시 경로입니다. 실제 경로에 맞게 수정하세요.
            }
        } catch (IOException e) {
            e.printStackTrace(); // 예외 처리는 적절히 수행하세요.
        }

        return null; // 업로드 실패 시 null 반환 또는 적절한 처리를 하세요.
    }



}
