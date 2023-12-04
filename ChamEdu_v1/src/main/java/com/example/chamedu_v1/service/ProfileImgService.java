package com.example.chamedu_v1.service;

import com.example.chamedu_v1.data.dto.MenteeProfileImgDto;
import com.example.chamedu_v1.data.dto.MentorProfileImgDto;
import com.example.chamedu_v1.data.entity.MenteeImageFile;
import com.example.chamedu_v1.data.entity.MentorImageFile;
import com.example.chamedu_v1.data.repository.MenteeProfileImgRepository;
import com.example.chamedu_v1.data.repository.MenteeRepository;
import com.example.chamedu_v1.data.repository.MentorProfileImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileImgService {

    private final MenteeProfileImgRepository menteeFileRepository;

    private final MentorProfileImgRepository mentorFileRepository;
    @Autowired
    public ProfileImgService(MenteeProfileImgRepository menteeFileRepository, MentorProfileImgRepository mentorFileRepository) {
        this.menteeFileRepository = menteeFileRepository;
        this.mentorFileRepository = mentorFileRepository;
    }


    /**
     * 멘토 프로필 이미지 개별 조회
     */
    @Transactional(readOnly = true)
    public MentorProfileImgDto findMentorProfileImgByFileId(int id){

        MentorImageFile mentorImageFile = mentorFileRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 파일이 존재하지 않습니다."));

        MentorProfileImgDto mentorProfileImgDto = MentorProfileImgDto.builder()
                .origFileName(mentorImageFile.getOrigFileName())
                .filePath(mentorImageFile.getFilePath())
                .fileSize(mentorImageFile.getFileSize())
                .build();

        return mentorProfileImgDto;
    }

    /**
     * 멘티 프로필 이미지 개별 조회
     */
    @Transactional(readOnly = true)
    public MenteeProfileImgDto findMenteeProfileImgByFileId(int id){

        MenteeImageFile menteeImageFile = menteeFileRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 파일이 존재하지 않습니다."));

        MenteeProfileImgDto menteeProfileImgDto = MenteeProfileImgDto.builder()
                .origFileName(menteeImageFile.getOrigFileName())
                .filePath(menteeImageFile.getFilePath())
                .fileSize(menteeImageFile.getFileSize())
                .build();

        return menteeProfileImgDto;
    }
}
