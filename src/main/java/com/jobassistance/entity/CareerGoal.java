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
 * キャリア目標エンティティ
 */
@Entity
@Table(name = "career_goals")
@Data
@EqualsAndHashCode(exclude = "worker")
@ToString(exclude = "worker")
@EntityListeners(AuditingEntityListener.class)
public class CareerGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDate targetDate;

    @Column
    private LocalDate achievedDate;

    @Column(length = 50)
    private String goalCategory; // スキル向上、資格取得、昇進、転職など

    @Column(columnDefinition = "TEXT")
    private String actionSteps; // アクションステップ（JSON形式）

    @Column(columnDefinition = "TEXT")
    private String successCriteria; // 成功基準

    @Column
    private Double progressPercentage; // 進捗率（0-100）

    @Column(length = 50)
    private String status = "設定済み"; // 設定済み、進行中、達成、見直し、放棄

    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
