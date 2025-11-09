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

/**
 * 技能訓練エンティティ
 */
@Entity
@Table(name = "skill_trainings")
@Data
@EqualsAndHashCode(exclude = "worker")
@ToString(exclude = "worker")
@EntityListeners(AuditingEntityListener.class)
public class SkillTraining {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @Column(nullable = false, length = 100)
    private String skillCategory;

    @Column(nullable = false, length = 200)
    private String skillName;

    @Column(nullable = false)
    private LocalDate trainingStartDate;

    @Column
    private LocalDate trainingEndDate;

    @Column
    private Integer trainingHours = 0;

    @Column(length = 200)
    private String trainingLocation;

    @Column(length = 100)
    private String instructor;

    @Column(length = 50)
    private String trainingMethod;

    @Column(length = 50)
    private String status = "受講中";

    @Column
    private Double completionRate;

    @Column
    private Double evaluationScore;

    @Column
    private Boolean certificateIssued = false;

    @Column(length = 100)
    private String certificateNumber;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}

