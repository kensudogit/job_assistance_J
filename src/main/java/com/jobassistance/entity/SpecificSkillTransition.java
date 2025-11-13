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
 * 特定技能移行エンティティ
 */
@Entity
@Table(name = "specific_skill_transitions")
@Data
@EqualsAndHashCode(exclude = "worker")
@ToString(exclude = "worker")
@EntityListeners(AuditingEntityListener.class)
public class SpecificSkillTransition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @Column(nullable = false)
    private LocalDate transitionDate;

    @Column(nullable = false, length = 100)
    private String fromSkill; // 移行前の技能

    @Column(nullable = false, length = 100)
    private String toSkill; // 移行先の技能

    @Column(length = 50)
    private String transitionType; // 技能実習、特定技能、一般就労など

    @Column(columnDefinition = "TEXT")
    private String reason; // 移行理由

    @Column(columnDefinition = "TEXT")
    private String requiredTraining; // 必要な訓練

    @Column(columnDefinition = "TEXT")
    private String supportProvided; // 提供された支援

    @Column(length = 50)
    private String status = "計画中"; // 計画中、進行中、完了

    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
