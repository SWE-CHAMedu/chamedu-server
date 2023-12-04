package com.example.chamedu_v1.controller;

import com.example.chamedu_v1.data.dto.MentorProfileImgDto;
import com.example.chamedu_v1.data.repository.ProfileRepository;
import com.example.chamedu_v1.service.MentorProfileDetailService;
import com.example.chamedu_v1.service.ProfileImgService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
@RestController
public class MentorProfileImgController {

    private final ProfileImgService profileImgService;

    @Autowired
    public MentorProfileImgController(ProfileImgService profileImgService, ProfileRepository profileRepository) {
        this.profileImgService = profileImgService;
    }

    /**
     * 이미지 개별 조회
     */
    @CrossOrigin
    @GetMapping(
            //image/{id}
            value = "/mentorProfileImg/{id}",
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}
    )
    public ResponseEntity<byte[]> getImage(@PathVariable int id) throws IOException {
        MentorProfileImgDto mentorProfileImgDto = profileImgService.findMentorProfileImgByFileId(id);
        String absolutePath
                = new File("").getAbsolutePath() + File.separator + File.separator;
        String filePath = mentorProfileImgDto.getFilePath();

        String path;

        String modifiedFilePath;
        if (absolutePath.contains("/")) {
            modifiedFilePath = filePath.replace("\\", "/");
        } else {
            modifiedFilePath = filePath.replace("/", "\\");
        }

        path = modifiedFilePath;


        InputStream imageStream = new FileInputStream(absolutePath + path);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();

        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
    }
    
}
