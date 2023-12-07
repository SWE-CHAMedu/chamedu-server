package com.example.chamedu_v1.data.entity;

import lombok.*;
import jakarta.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(nullable = false)
    private int chamQuantity;

    @Column(nullable = false)
    private int totalCost;

    @Column(nullable = false)
    private String paymentStatus;

    @ManyToOne
    @JoinColumn(name = "mentee_id")
    private Mentee mentee;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;


}
