package com.example.chamedu_v1.data.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.List;

@Getter
@NoArgsConstructor
public class AvailableTimeDto {
    List<Time> availableTimeList;

}
