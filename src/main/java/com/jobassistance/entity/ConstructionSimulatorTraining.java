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
 * 建設シミュレーター訓練エンティティ
 */
@Entity
@Table(name = "construction_simulator_trainings")
@Data
@EqualsAndHashCode(exclude = "worker")
@ToString(exclude = "worker")
@EntityListeners(AuditingEntityListener.class)
public class ConstructionSimulatorTraining {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_menu_id")
    private TrainingMenu trainingMenu;

    @Column(nullable = false)
    private LocalDate trainingDate;

    @Column(nullable = false, length = 100)
    private String equipmentType; // 油圧ショベル、ブルドーザーなど

    @Column(length = 20)
    private String difficultyLevel; // 初級、中級、上級

    @Column
    private Integer timeLimit; // 制限時間（秒）

    @Column
    private Integer actualTime; // 実際の時間（秒）

    @Column
    private Double safetyScore; // 安全動作率（0-100）

    @Column
    private Integer errorCount; // エラー件数

    @Column
    private Double procedureCompliance; // 手順遵守率（0-100）

    @Column
    private Double achievementRate; // 達成度（0-100）

    @Column(columnDefinition = "TEXT")
    private String sessionData; // セッションデータ（JSON形式）

    @Column(length = 50)
    private String status = "実施中"; // 実施中、完了、中断

    @Column(columnDefinition = "TEXT")
    private String feedback;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
