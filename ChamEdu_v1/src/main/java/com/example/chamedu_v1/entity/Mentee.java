package com.example.chamedu_v1.entity;

import jakarta.validation.constraints.Email;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "mentee")
public class Mentee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menteeId")
    private String menteeId;


    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String name;

    @Email
    @Column(nullable = false)
    private String email;

}
