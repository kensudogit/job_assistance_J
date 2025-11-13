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
import java.util.ArrayList;
import java.util.List;

/**
 * 外国人就労者エンティティ
 */
@Entity
@Table(name = "workers")
@Data
@EqualsAndHashCode(exclude = { "progressRecords", "documents", "notifications", "trainingEnrollments",
        "evaluations", "messages", "calendarEvents", "reports", "japaneseProficiencies",
        "skillTrainings", "japaneseLearningRecords", "preDepartureSupports" })
@ToString(exclude = { "progressRecords", "documents", "notifications", "trainingEnrollments",
        "evaluations", "messages", "calendarEvents", "reports", "japaneseProficiencies",
        "skillTrainings", "japaneseLearningRecords", "preDepartureSupports" })
@EntityListeners(AuditingEntityListener.class)
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100)
    private String nameKana;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(length = 500)
    private String phone;

    @Column(length = 200)
    private String address;

    @Column
    private LocalDate birthDate;

    @Column(length = 100)
    private String nationality;

    @Column(length = 50)
    private String nativeLanguage;

    @Column(length = 50)
    private String visaStatus;

    @Column
    private LocalDate visaExpiryDate;

    @Column(length = 20)
    private String japaneseLevel;

    @Column(length = 20)
    private String englishLevel;

    @Column(columnDefinition = "TEXT")
    private String skills;

    @Column
    private Integer experienceYears = 0;

    @Column(length = 200)
    private String education;

    @Column(length = 50)
    private String currentStatus = "登録中";

    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // リレーション
    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkerProgress> progressRecords = new ArrayList<>();

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents = new ArrayList<>();

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainingEnrollment> trainingEnrollments = new ArrayList<>();

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evaluation> evaluations = new ArrayList<>();

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CalendarEvent> calendarEvents = new ArrayList<>();

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JapaneseProficiency> japaneseProficiencies = new ArrayList<>();

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SkillTraining> skillTrainings = new ArrayList<>();

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JapaneseLearningRecord> japaneseLearningRecords = new ArrayList<>();

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PreDepartureSupport> preDepartureSupports = new ArrayList<>();
}
