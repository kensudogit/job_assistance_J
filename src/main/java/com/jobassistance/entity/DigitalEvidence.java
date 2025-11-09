package com.jobassistance.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * デジタル証拠エンティティ
 */
@Entity
@Table(name = "digital_evidences")
@Data
@EqualsAndHashCode(exclude = "worker")
@ToString(exclude = "worker")
@EntityListeners(AuditingEntityListener.class)
public class DigitalEvidence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 50)
    private String evidenceType; // 訓練記録、評価、証明書、レポートなど

    @Column(length = 500)
    private String filePath; // ファイルパスまたはURL

    @Column(length = 200)
    private String fileName;

    @Column
    private Long fileSize; // ファイルサイズ（バイト）

    @Column(length = 100)
    private String mimeType; // MIMEタイプ

    @Column(length = 64)
    private String hashValue; // ファイルハッシュ値（整合性確認用）

    @Column
    private Boolean isVerified = false; // 検証済みかどうか

    @Column(length = 100)
    private String verifiedBy; // 検証者

    @Column
    private LocalDateTime verifiedAt; // 検証日時

    @Column(columnDefinition = "TEXT")
    private String metadata; // メタデータ（JSON形式）

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}

