package com.example.chamedu_v1.data.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class MenteeProfileUpdateDto {
    private String nickName;
    private String info;
    private String wishUniv;
    private int wishCollege;
    private int wishAdmissionType;
    private MultipartFile userImg;
}
