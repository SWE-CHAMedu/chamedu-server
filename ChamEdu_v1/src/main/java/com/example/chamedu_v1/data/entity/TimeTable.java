package com.example.chamedu_v1.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="timetable")
public class TimeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "time_id")
    private int timeId;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;

    @Column
    private Date availableTime;

}