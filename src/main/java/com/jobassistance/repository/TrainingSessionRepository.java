package com.jobassistance.repository;

import com.jobassistance.entity.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 訓練セッションリポジトリ
 */
@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {

    Optional<TrainingSession> findBySessionId(String sessionId);

    List<TrainingSession> findByWorkerId(Long workerId);

    List<TrainingSession> findByTrainingMenuId(Long trainingMenuId);
}
