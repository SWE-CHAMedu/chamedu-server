package com.example.chamedu_v1.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ChatInfoDto{
        private int roomId;
        private String menteeName;
        private String startDate;
        private String durationTime;
        private String chatTitle;
}
