package com.example.chamedu_v1.data.dto;

import com.example.chamedu_v1.data.entity.MenteeImageFile;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class MenteeProfileImgDto {
    private int id;
    private int menteeId;
    private String origFileName;
    private String filePath;  // 파일 저장 경로
    private Long fileSize;

    //멘토용
    public MenteeProfileImgDto(MenteeImageFile menteeImageFile) {
        id = menteeImageFile.getId();
        menteeId = menteeImageFile.getMentee().getMenteeId();
        origFileName = menteeImageFile.getOrigFileName();
        filePath = menteeImageFile.getFilePath();
        fileSize = menteeImageFile.getFileSize();
    }

    //필요하다면 userNumber 도 parm으로
    public MenteeProfileImgDto(MenteeImageFile menteeImageFile, int menteeId) {
        id = menteeImageFile.getId();
        this.menteeId = menteeId;
    }


    @Builder
    public MenteeProfileImgDto(String origFileName, String filePath, Long fileSize) {
        this.origFileName = origFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
}