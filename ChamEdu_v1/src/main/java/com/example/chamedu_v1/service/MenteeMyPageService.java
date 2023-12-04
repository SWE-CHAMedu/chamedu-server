package com.example.chamedu_v1.service;

import com.example.chamedu_v1.data.dto.*;
import com.example.chamedu_v1.data.entity.*;
import com.example.chamedu_v1.data.repository.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
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

        // 멘티 userId로 해당 게시글 첨부파일 전체 조회
        MenteeImageFile menteeImageFile = menteeProfileImgRepository.findByMentee_UserId(userId);
        int fileId=menteeImageFile.getId();

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

        myPageDto.setUserImg(fileId);
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
                    roomDto.setTitle(roomDto.getTitle());
                    roomDto.setMentorName(room.getMentor().getNickname());
                    // 필요한 다른 정보들도 설정해야 합니다.
                    return roomDto;
                })
                .collect(Collectors.toList());

        myPageDto.setReqeustRoomList(roomDtoList);
        return myPageDto;
    }


    public Mentee updateMenteeProfile(String userId, MenteeProfileUpdateDto profileUpdateDto, MultipartFile file){
//        String content=profileUpdateDto.nickName();
//        String title=profileUpdateDto.getTitle();
//
//        Post post=new Post();
//        post.setContent(content);
//        post.setCreatedDate(new Timestamp(System.currentTimeMillis()));
//        post.setUser(userRepository.findByUsername(authentication.getName()));
//        post.setTitle(title);
//
//        List<PostFile> postFileList = fileHandler.parseFileInfo(post,files);
//        int userNumber=userRepository.findByUsername(authentication.getName()).getUserNumber();
//
//        // 파일이 존재할 때에만 처리
//        if(!postFileList.isEmpty()) {
//            for(PostFile postFile : postFileList) {
//                // 파일을 DB에 저장
//                post.addPostFile(postFileRepository.save(postFile));
//                //PostFile saveFile=PostFileRepository.save(postFile);
//            }
//            post.setPostFileList(postFileList);
//
//        }
//        Post savePost=postRepository.save(post);
//        return new PostDto(savePost,userNumber);
        Mentee menteeInfo = menteeRepository.findByUserId(userId);
        MenteeImageFile menteeImageFile = menteeProfileImgRepository.findByMentee_UserId(userId);

        String imageUrl = uploadProfileImage(file);

        if (menteeImageFile == null) {
            menteeImageFile = MenteeImageFile.builder()
                    .origFileName(file.getOriginalFilename())
                    .filePath(imageUrl)
                    .fileSize(file.getSize())
                    .mentee(menteeInfo)
                    .build();
        } else {
            // 이미지가 존재하는 경우 기존 이미지 업데이트
            menteeImageFile.setOrigFileName(file.getOriginalFilename());
            menteeImageFile.setFilePath(imageUrl);
            menteeImageFile.setFileSize(file.getSize());
        }


        menteeInfo.setNickname(profileUpdateDto.getNickName());
        menteeInfo.setInfo(profileUpdateDto.getInfo());
        menteeInfo.setWishUniv(profileUpdateDto.getWishUniv());
        menteeInfo.setWishCollege(profileUpdateDto.getWishCollege());
        menteeInfo.setWishAdmissionType(profileUpdateDto.getWishAdmissionType());


        menteeRepository.save(menteeInfo);
        menteeProfileImgRepository.save(menteeImageFile);

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
//
//    public List<ReviewDto> getReviewList(int menteeUserId) {
//        reviewRepository.findAllByMenteeUserId(menteeUserId);
//        return "리뷰가 삭제되었습니다.";
//    }

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
