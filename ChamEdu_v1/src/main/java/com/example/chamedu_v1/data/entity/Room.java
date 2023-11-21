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
@Table(name="room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomId")
    private int roomId;

    @ManyToOne
    @JoinColumn(name = "mentee_id")
    private Mentee mentee;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;

    @Column(nullable = false)
    private LocalDateTime maxMinute;

    @Column(nullable = false)
    private int point;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @Column(columnDefinition = "CHAR(1) DEFAULT 'w' NOT NULL")
    private char status;
}
