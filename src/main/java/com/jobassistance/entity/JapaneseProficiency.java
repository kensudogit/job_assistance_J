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
 * 日本語能力管理エンティティ
 */
@Entity
@Table(name = "japanese_proficiencies")
@Data
@EqualsAndHashCode(exclude = "worker")
@ToString(exclude = "worker")
@EntityListeners(AuditingEntityListener.class)
public class JapaneseProficiency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @Column(nullable = false)
    private LocalDate testDate;

    @Column(nullable = false, length = 50)
    private String testType;

    @Column(length = 20)
    private String level;

    @Column
    private Integer readingScore;

    @Column
    private Integer listeningScore;

    @Column
    private Integer writingScore;

    @Column
    private Integer speakingScore;

    @Column
    private Integer totalScore;

    @Column
    private Boolean passed = false;

    @Column(length = 100)
    private String certificateNumber;

    @Column
    private LocalDate certificateIssuedDate;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}

