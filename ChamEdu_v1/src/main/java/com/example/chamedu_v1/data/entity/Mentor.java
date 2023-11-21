package com.example.chamedu_v1.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="Mentor")
public class Mentor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mentorId")
    private Long menteeId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;


}
