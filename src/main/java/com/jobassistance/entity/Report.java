package com.jobassistance.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * レポート・統計エンティティ
 */
@Entity
@Table(name = "reports")
@Data
@EqualsAndHashCode(exclude = "worker")
@ToString(exclude = "worker")
@EntityListeners(AuditingEntityListener.class)
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id")
    private Worker worker;

    @Column(nullable = false, length = 50)
    private String reportType;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String reportData;

    @Column
    private LocalDate periodStart;

    @Column
    private LocalDate periodEnd;

    @Column(length = 100)
    private String generatedBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime generatedAt;
}
