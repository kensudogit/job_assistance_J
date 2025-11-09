package com.jobassistance.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 訓練セッションエンティティ（Unityから送信される訓練結果）
 */
@Entity
@Table(name = "training_sessions")
@Data
@EqualsAndHashCode(exclude = {"worker", "trainingMenu", "kpiScores", "operationLogs"})
@ToString(exclude = {"worker", "trainingMenu", "kpiScores", "operationLogs"})
@EntityListeners(AuditingEntityListener.class)
public class TrainingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String sessionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id")
    private Worker worker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_menu_id")
    private TrainingMenu trainingMenu;

    @Column(nullable = false)
    private LocalDateTime sessionStartTime;

    @Column(nullable = false)
    private LocalDateTime sessionEndTime;

    @Column
    private Integer durationSeconds;

    @Column(columnDefinition = "TEXT")
    private String aiEvaluationJson;

    @Column(columnDefinition = "TEXT")
    private String replayDataJson;

    @Column(length = 50)
    private String status = "完了";

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // リレーション
    @OneToMany(mappedBy = "trainingSession", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KPIScore> kpiScores = new ArrayList<>();

    @OneToMany(mappedBy = "trainingSession", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OperationLog> operationLogs = new ArrayList<>();
}

