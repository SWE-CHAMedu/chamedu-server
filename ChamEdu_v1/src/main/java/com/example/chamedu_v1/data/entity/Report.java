package com.example.chamedu_v1.data.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

    @Column(nullable = false)
    private int reportType;

    @Column(nullable = false)
    private LocalDateTime createTime;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String reportDetail;

    @ManyToOne
    @JoinColumn(name = "mentee_id")
    private Mentee mentee;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;

}
