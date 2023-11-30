package com.example.chamedu_v1.data.dto;

import lombok.*;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class MentorProfileUpdateRequestDto {

    private String nickName;
    private String promotionText;
    private String userImg;
    private String university;
    private int college;
    private List<String> availableTime;

    public List<Time> convertToTimeList() {
        List<Time> timeList = new ArrayList<>();
        if (availableTime != null) {
            for (String timeString : availableTime) {
                try {
                    // 문자열을 Date 객체로 파싱
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                    Date parsedDate = dateFormat.parse(timeString);

                    // Date 객체에서 java.sql.Time 객체를 만듦
                    Time sqlTime = new Time(parsedDate.getTime());

                    // java.sql.Time 객체를 리스트에 추가
                    timeList.add(sqlTime);
                } catch (ParseException e) {
                    // 예외 처리 (예: 오류 기록)
                    e.printStackTrace(); // 예시로 사용한 것이므로 예외를 적절히 기록해야 합니다.
                }
            }
        }
        return timeList;
    }
}
