package com.jobassistance.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 研修・トレーニングエンティティ
 */
@Entity
@Table(name = "trainings")
@Data
@EqualsAndHashCode(exclude = "enrollments")
@ToString(exclude = "enrollments")
@EntityListeners(AuditingEntityListener.class)
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 50)
    private String trainingType;

    @Column(length = 50)
    private String category;

    @Column
    private Integer durationHours;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column(length = 200)
    private String location;

    @Column(length = 100)
    private String instructor;

    @Column
    private Integer maxParticipants;

    @Column
    private Integer currentParticipants = 0;

    @Column(length = 50)
    private String status = "予定";

    @Column(columnDefinition = "TEXT")
    private String materials;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // リレーション
    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainingEnrollment> enrollments = new ArrayList<>();
}

