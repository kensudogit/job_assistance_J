package com.jobassistance.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 訓練メニューエンティティ（Unityシミュレーター用）
 */
@Entity
@Table(name = "training_menus")
@Data
@EqualsAndHashCode(exclude = {"trainingSessions", "menuAssignments"})
@ToString(exclude = {"trainingSessions", "menuAssignments"})
@EntityListeners(AuditingEntityListener.class)
public class TrainingMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String menuName;

    @Column(nullable = false, length = 100)
    private String scenarioId;

    @Column(columnDefinition = "TEXT")
    private String scenarioDescription;

    @Column
    private Double targetSafetyScore;

    @Column
    private Integer targetErrorCount;

    @Column
    private Double targetProcedureCompliance;

    @Column
    private Integer targetWorkTime;

    @Column
    private Double targetAchievementRate;

    @Column(nullable = false, length = 100)
    private String equipmentType;

    @Column(nullable = false, length = 20)
    private String difficultyLevel;

    @Column
    private Integer timeLimit;

    @Column
    private Boolean isActive = true;

    @Column(length = 100)
    private String createdBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // リレーション
    @OneToMany(mappedBy = "trainingMenu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainingSession> trainingSessions = new ArrayList<>();

    @OneToMany(mappedBy = "trainingMenu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainingMenuAssignment> menuAssignments = new ArrayList<>();
}

