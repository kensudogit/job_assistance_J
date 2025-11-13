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
 * 来日前支援エンティティ
 */
@Entity
@Table(name = "pre_departure_supports")
@Data
@EqualsAndHashCode(exclude = "worker")
@ToString(exclude = "worker")
@EntityListeners(AuditingEntityListener.class)
public class PreDepartureSupport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @Column(nullable = false, length = 50)
    private String supportType;

    @Column(nullable = false)
    private LocalDate supportDate;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String supportContent;

    @Column(length = 50)
    private String status = "予定";

    @Column(columnDefinition = "TEXT")
    private String requiredDocuments;

    @Column
    private Boolean documentsSubmitted = false;

    @Column(length = 100)
    private String supportStaff;

    @Column(columnDefinition = "TEXT")
    private String nextAction;

    @Column
    private LocalDate nextActionDate;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
