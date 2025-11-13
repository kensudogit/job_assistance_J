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
 * 就労支援進捗管理エンティティ
 */
@Entity
@Table(name = "worker_progress")
@Data
@EqualsAndHashCode(exclude = "worker")
@ToString(exclude = "worker")
@EntityListeners(AuditingEntityListener.class)
public class WorkerProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @Column(nullable = false)
    private LocalDate progressDate;

    @Column(nullable = false, length = 50)
    private String progressType;

    @Column(length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 50)
    private String status = "実施中";

    @Column(columnDefinition = "TEXT")
    private String supportContent;

    @Column(columnDefinition = "TEXT")
    private String nextAction;

    @Column
    private LocalDate nextActionDate;

    @Column(length = 100)
    private String supportStaff;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
