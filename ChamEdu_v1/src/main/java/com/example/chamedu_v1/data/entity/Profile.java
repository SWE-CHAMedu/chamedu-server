package com.example.chamedu_v1.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Time;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profileId")
    private int profileId;

    @Column
    private String university;

    @Column
    @ColumnDefault("0")
    private int college;

    @Column
    @ColumnDefault("0")
    private int admissionType;

    @Column
    @ColumnDefault("안녕하세요")
    private String promotionText;

    @Column
    @ColumnDefault("0")
    private int auth;

    @Column
    @ColumnDefault("0")
    private float avgScore;

    @OneToOne
    @JoinColumn(name ="mentorId")
    private Mentor mentor;

}