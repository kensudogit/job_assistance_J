package com.jobassistance.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * KPIスコアエンティティ
 */
@Entity
@Table(name = "kpi_scores")
@Data
@EqualsAndHashCode(exclude = "trainingSession")
@ToString(exclude = "trainingSession")
@EntityListeners(AuditingEntityListener.class)
public class KPIScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_session_id", nullable = false)
    private TrainingSession trainingSession;

    @Column
    private Double safetyScore;

    @Column
    private Integer errorCount = 0;

    @Column
    private Double procedureComplianceRate;

    @Column
    private Integer workTimeSeconds;

    @Column
    private Double achievementRate;

    @Column
    private Double accuracyScore;

    @Column
    private Double efficiencyScore;

    @Column
    private Double overallScore;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}

