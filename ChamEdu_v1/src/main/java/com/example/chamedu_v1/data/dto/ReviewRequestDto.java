package com.example.chamedu_v1.data.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewRequestDto {
    private String title;
    private String content;
    private int score;
}
