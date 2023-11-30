package com.example.chamedu_v1.data.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserIdRequestDto {
    private String userId;

    public UserIdRequestDto() {
    }
    public UserIdRequestDto(String userId) {
        this.userId = userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}