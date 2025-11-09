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
 * 訓練メニュー割り当てエンティティ
 */
@Entity
@Table(name = "training_menu_assignments")
@Data
@EqualsAndHashCode(exclude = {"worker", "trainingMenu"})
@ToString(exclude = {"worker", "trainingMenu"})
@EntityListeners(AuditingEntityListener.class)
public class TrainingMenuAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_menu_id", nullable = false)
    private TrainingMenu trainingMenu;

    @Column(nullable = false)
    private LocalDate assignedDate;

    @Column
    private LocalDate deadline;

    @Column(length = 50)
    private String status = "未開始";

    @Column
    private LocalDateTime completedAt;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}

