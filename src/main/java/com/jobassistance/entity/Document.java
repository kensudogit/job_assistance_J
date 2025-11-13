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
 * ドキュメント管理エンティティ
 */
@Entity
@Table(name = "documents")
@Data
@EqualsAndHashCode(exclude = "worker")
@ToString(exclude = "worker")
@EntityListeners(AuditingEntityListener.class)
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @Column(nullable = false, length = 50)
    private String documentType;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 500)
    private String filePath;

    @Column(length = 200)
    private String fileName;

    @Column
    private Long fileSize;

    @Column(length = 100)
    private String mimeType;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private LocalDate expiryDate;

    @Column
    private Boolean isRequired = false;

    @Column
    private Boolean isVerified = false;

    @Column(length = 100)
    private String uploadedBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
