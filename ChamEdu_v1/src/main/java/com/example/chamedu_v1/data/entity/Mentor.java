package com.example.chamedu_v1.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="mentor")
public class Mentor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mentor_id")
    private int mentorId;

    @Column(unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String university;

    @Column
    private String userImg;

    @Column
    @ColumnDefault("0")
    private int point;

    @OneToMany(mappedBy = "mentor")
    private List<Review> ReviewList = new ArrayList<Review>();

    @Builder // @Setter를 두지 않기 위한 어노테이션
    public Mentor(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    protected void Member() {} //기본 생성자는 protected 로 막는다.




}