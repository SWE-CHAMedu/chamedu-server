package com.example.chamedu_v1.controller;

import com.example.chamedu_v1.data.dto.PointChangeRequestDto;
import com.example.chamedu_v1.service.PointManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PointManageController {

    private final PointManageService pointManageService;

    @Autowired
    public PointManageController(PointManageService pointManageService) {
        this.pointManageService = pointManageService;
    }

    // 구매목록 조회
    @GetMapping("/point")
    public ResponseEntity<List<Integer>> visitShop() {
        List<Integer> products = Arrays.asList(100, 500, 1000, 10000); // 몇 참 구매할 건지
        return ResponseEntity.ok(products);
    }

    // 구매하기
    @PostMapping("/point/charge")
    public ResponseEntity<String> chargePoints(@RequestBody PointChangeRequestDto chargeDto) {
        int amount= chargeDto.getChangedPoints();
        // 결제 먼저 진행 후
        pointManageService.purchasePoints(chargeDto); // 참포인트 ++
        return ResponseEntity.ok(amount+"참을 구매하였습니다!");
    }

    // 환전하기
    @PostMapping("/point/exchange")
    public ResponseEntity<String> exchangePoints(@RequestBody PointChangeRequestDto exchangeDto) {
        int amount= exchangeDto.getChangedPoints();
        // 현금화 먼저 진행 후
        pointManageService.refundPoints(exchangeDto); // 참포인트 --
        return ResponseEntity.ok(amount+"참을 환전하였습니다!");
    }
}
