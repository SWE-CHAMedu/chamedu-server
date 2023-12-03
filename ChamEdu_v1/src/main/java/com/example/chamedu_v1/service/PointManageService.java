package com.example.chamedu_v1.service;

import com.example.chamedu_v1.data.dto.PointChangeRequestDto;

public interface PointManageService {
    void purchasePoints(PointChangeRequestDto pointChangeRequestDto);
    void refundPoints(PointChangeRequestDto pointChangeRequestDto);
}
