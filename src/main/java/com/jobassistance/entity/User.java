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
 * ユーザーエンティティ（認証・認可）
 */
@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(exclude = "worker")
@ToString(exclude = {"passwordHash", "worker"})
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String username;

    @Column(nullable = false, length = 256)
    private String passwordHash;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 50)
    private String role = "trainee"; // trainee, administrator, auditor

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id")
    private Worker worker;

    @Column
    private Boolean isActive = true;

    @Column
    private LocalDateTime lastLogin;

    // 多要素認証（MFA）関連フィールド
    @Column
    private Boolean mfaEnabled = false;

    @Column(length = 32)
    private String mfaSecret;

    @Column(columnDefinition = "TEXT")
    private String backupCodes;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}

