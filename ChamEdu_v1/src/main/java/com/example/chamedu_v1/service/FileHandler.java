package com.example.chamedu_v1.service;


import com.example.chamedu_v1.data.dto.MenteeProfileImgDto;
import com.example.chamedu_v1.data.dto.MentorProfileImgDto;
import com.example.chamedu_v1.data.entity.Mentee;
import com.example.chamedu_v1.data.entity.MenteeImageFile;
import com.example.chamedu_v1.data.entity.Mentor;
import com.example.chamedu_v1.data.entity.MentorImageFile;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class FileHandler {

    public MentorImageFile parseMentorFileInfo(Mentor mentor, MultipartFile multipartFile) throws Exception {
        // 반환할 파일

        MentorImageFile mentorFileEmpty = new MentorImageFile();
        // 전달되어 온 파일이 존재할 경우
        if(!multipartFile.isEmpty()){
            // 파일명을 업로드 한 날짜로 변환하여 저장
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String current_date = now.format(dateTimeFormatter);

            // 프로젝트 디렉터리 내의 저장을 위한 절대 경로 설정
            // 경로 구분자 File.separator 사용
            String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;

            // 파일을 저장할 세부 경로 지정
            String path = "images" + File.separator + current_date;
            File file = new File(path);

            // 디렉터리가 존재하지 않을 경우
            if (!file.exists()) {
                boolean wasSuccessful = file.mkdirs();

                // 디렉터리 생성에 실패했을 경우
                if (!wasSuccessful)
                    System.out.println("file: was not successful");
            }

            String originalFileExtension;
            String contentType = multipartFile.getContentType();
            //파일 처리
            while (!ObjectUtils.isEmpty(contentType)) {

                if (contentType.contains("image/jpeg"))
                    originalFileExtension = ".jpg";
                else if (contentType.contains("image/png"))
                    originalFileExtension = ".png";
                else {  // 다른 확장자일 경우 처리 x
                    System.out.println("jpeg,png 형식 파일만 저장 가능합니다.");
                    break;
                }

                // 파일명 중복 피하고자 나노초까지 얻어와 지정
                String new_file_name = System.nanoTime() + originalFileExtension;
                // 파일 DTO 생성
                MentorProfileImgDto mentorFileDto = MentorProfileImgDto.builder()
                        .origFileName(multipartFile.getOriginalFilename())
                        .filePath(path + File.separator + new_file_name)
                        .fileSize(multipartFile.getSize())
                        .build();

                // 파일 DTO 이용하여 MentorFile 엔티티 생성
                MentorImageFile mentorFile = new MentorImageFile(
                        mentorFileDto.getOrigFileName(),
                        mentorFileDto.getFilePath(),
                        mentorFileDto.getFileSize()
                );


                // 업로드 한 파일 데이터를 지정한 파일에 저장
                file = new File(absolutePath + path + File.separator + new_file_name);
                multipartFile.transferTo(file);

                // 파일 권한 설정(쓰기, 읽기)
                file.setWritable(true);
                file.setReadable(true);


                return mentorFile;
            }
        }
            return mentorFileEmpty;
    }


    public MenteeImageFile parseMenteeFileInfo(Mentee mentee, MultipartFile multipartFile  ) throws Exception {
        // 반환할 파일
        MenteeImageFile mentorFileEmpty = new MenteeImageFile();

        // 전달되어 온 파일이 존재할 경우
        if (!multipartFile.isEmpty()) {
            // 파일명을 업로드 한 날짜로 변환하여 저장
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String current_date = now.format(dateTimeFormatter);

            // 프로젝트 디렉터리 내의 저장을 위한 절대 경로 설정
            // 경로 구분자 File.separator 사용
            String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;

            // 파일을 저장할 세부 경로 지정
            String path = "images" + File.separator + current_date;
            File file = new File(path);

            // 디렉터리가 존재하지 않을 경우
            if (!file.exists()) {
                boolean wasSuccessful = file.mkdirs();

                // 디렉터리 생성에 실패했을 경우
                if (!wasSuccessful)
                    System.out.println("file: was not successful");
            }

            String originalFileExtension;
            String contentType = multipartFile.getContentType();
            //파일 처리
            while (!ObjectUtils.isEmpty(contentType)) {

                if (contentType.contains("image/jpeg"))
                    originalFileExtension = ".jpg";
                else if (contentType.contains("image/png"))
                    originalFileExtension = ".png";
                else {  // 다른 확장자일 경우 처리 x
                    System.out.println("jpeg,png 형식 파일만 저장 가능합니다.");
                    break;
                }

                // 파일명 중복 피하고자 나노초까지 얻어와 지정
                String new_file_name = System.nanoTime() + originalFileExtension;
                // 파일 DTO 생성
                MenteeProfileImgDto menteeFileDto = MenteeProfileImgDto.builder()
                        .origFileName(multipartFile.getOriginalFilename())
                        .filePath(path + File.separator + new_file_name)
                        .fileSize(multipartFile.getSize())
                        .build();

                // 파일 DTO 이용하여 MenteeFile 엔티티 생성
                MenteeImageFile menteeFile = new MenteeImageFile(
                        menteeFileDto.getOrigFileName(),
                        menteeFileDto.getFilePath(),
                        menteeFileDto.getFileSize()
                );


                // 업로드 한 파일 데이터를 지정한 파일에 저장
                file = new File(absolutePath + path + File.separator + new_file_name);
                multipartFile.transferTo(file);

                // 파일 권한 설정(쓰기, 읽기)
                file.setWritable(true);
                file.setReadable(true);

                return menteeFile;
            }
        }
        return mentorFileEmpty;
    }
}


