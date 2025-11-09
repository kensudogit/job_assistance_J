package com.jobassistance.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 操作ログエンティティ（操作タイムライン記録）
 */
@Entity
@Table(name = "operation_logs")
@Data
@EqualsAndHashCode(exclude = "trainingSession")
@ToString(exclude = "trainingSession")
@EntityListeners(AuditingEntityListener.class)
public class OperationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_session_id", nullable = false)
    private TrainingSession trainingSession;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false, length = 100)
    private String operationType;

    @Column
    private Double operationValue;

    @Column(columnDefinition = "TEXT")
    private String equipmentState;

    @Column
    private Double positionX;

    @Column
    private Double positionY;

    @Column
    private Double positionZ;

    @Column
    private Double velocity;

    @Column
    private Boolean errorEvent = false;

    @Column(columnDefinition = "TEXT")
    private String errorDescription;

    @Column
    private Boolean achievementEvent = false;

    @Column(columnDefinition = "TEXT")
    private String achievementDescription;

    @Column(length = 50)
    private String eventType;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}

