package com.example.chamedu_v1.data.dto;

import lombok.Data;

@Data
public class ReviewMyPageResponseDto {

    private int reviewId;

    private String title;

    private int point;

    private String content;
}
