package com.example.chamedu_v1.data.entity;


import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private int reviewId;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private String  title;

    @Column(nullable = false)
    private String content;

    @CreationTimestamp
    private Timestamp createdTime;

    @CreationTimestamp
    private Timestamp updateTime;

    @ManyToOne
    @JoinColumn(name = "mentee_id")
    private Mentee mentee;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;


}

