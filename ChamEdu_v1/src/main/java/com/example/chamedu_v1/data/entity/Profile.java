package com.example.chamedu_v1.data.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private int college;

    @Column
    private int admissionType;

    @Column
    private String promotionText;

    @Column
    private int auth;

    @Column
    private float avgScore;

    @Column
    private String profileImg;

    @OneToOne
    @JoinColumn(name ="mentorId")
    private Mentor mentor;

}