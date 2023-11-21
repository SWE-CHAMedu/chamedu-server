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
@Table(name="message")
public class Message {

    @Id
    @Column(name = "session_id")
    private Long sessionId; // 자식 테이블의 기본 키

    @OneToOne
    @MapsId
    @JoinColumn(name = "session_id")
    private Room room; // 부모 테이블의 기본 키를 참조하는 외래 키

    @Column(nullable = false)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date sendTime;

    @Column(nullable = false)
    private String sender;

}
