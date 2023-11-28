package com.example.chamedu_v1.data.entity;

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
    @Column(name = "mentee_id")
    private int menteeId;


    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String name;

    @Column
    private String wishAdmissionType;

    @Column
    private String wishCollege;

    @Column
    private String info;

    @Column
    private String wishUniv;

    @OneToMany(mappedBy = "mentee",cascade = CascadeType.REMOVE)
    private List<Payment> paymentList = new ArrayList<>();


}