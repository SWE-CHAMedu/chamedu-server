package com.example.chamedu_v1.data.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
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

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "mentee_id")
    private Mentee mentee;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date endDate;

    @Column(columnDefinition = "CHAR(1) DEFAULT 'W' NOT NULL")
    private char status;

    @Column
    private String chatTitle;
}
