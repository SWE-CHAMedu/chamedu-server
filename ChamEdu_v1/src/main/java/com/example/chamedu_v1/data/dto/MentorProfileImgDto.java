package com.example.chamedu_v1.data.dto;


import com.example.chamedu_v1.data.entity.MentorImageFile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class MentorProfileImgDto {
    private int id;
    private int mentorId;
    private String origFileName;
    private String filePath;  // 파일 저장 경로
    private Long fileSize;

    //멘토용
    public MentorProfileImgDto(MentorImageFile mentorImageFile) {
        id = mentorImageFile.getId();
        mentorId = mentorImageFile.getMentor().getMentorId();
        origFileName = mentorImageFile.getOrigFileName();
        filePath = mentorImageFile.getFilePath();
        fileSize = mentorImageFile.getFileSize();
    }

    //필요하다면 userNumber 도 parm으로
    public MentorProfileImgDto(MentorImageFile mentorImageFile, int mentorId) {
        id = mentorImageFile.getId();
        this.mentorId = mentorId;
    }

    
    @Builder
    public MentorProfileImgDto(String origFileName, String filePath, Long fileSize) {
        this.origFileName = origFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
}