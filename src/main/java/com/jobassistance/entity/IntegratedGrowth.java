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
 * 統合成長エンティティ
 */
@Entity
@Table(name = "integrated_growths")
@Data
@EqualsAndHashCode(exclude = "worker")
@ToString(exclude = "worker")
@EntityListeners(AuditingEntityListener.class)
public class IntegratedGrowth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @Column(nullable = false)
    private LocalDate recordDate;

    @Column
    private Double japaneseProficiencyScore; // 日本語能力スコア

    @Column
    private Double technicalSkillScore; // 技術スキルスコア

    @Column
    private Double safetyAwarenessScore; // 安全意識スコア

    @Column
    private Double communicationScore; // コミュニケーションスコア

    @Column
    private Double overallScore; // 総合スコア

    @Column(columnDefinition = "TEXT")
    private String growthAreas; // 成長領域（カンマ区切り）

    @Column(columnDefinition = "TEXT")
    private String improvementPlan; // 改善計画

    @Column(columnDefinition = "TEXT")
    private String achievements; // 達成事項

    @Column(columnDefinition = "TEXT")
    private String challenges; // 課題

    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}

