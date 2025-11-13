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
 * 日本語学習記録エンティティ
 */
@Entity
@Table(name = "japanese_learning_records")
@Data
@EqualsAndHashCode(exclude = "worker")
@ToString(exclude = "worker")
@EntityListeners(AuditingEntityListener.class)
public class JapaneseLearningRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @Column(nullable = false)
    private LocalDate learningDate;

    @Column(nullable = false, length = 50)
    private String learningType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String learningContent;

    @Column(columnDefinition = "TEXT")
    private String topicsCovered;

    @Column
    private Integer durationMinutes;

    @Column
    private Integer vocabularyLearned;

    @Column(columnDefinition = "TEXT")
    private String grammarPoints;

    @Column(columnDefinition = "TEXT")
    private String practiceActivities;

    @Column(length = 20)
    private String difficultyLevel;

    @Column
    private Integer selfRating;

    @Column(columnDefinition = "TEXT")
    private String instructorFeedback;

    @Column(columnDefinition = "TEXT")
    private String homeworkAssigned;

    @Column
    private Boolean homeworkCompleted = false;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
