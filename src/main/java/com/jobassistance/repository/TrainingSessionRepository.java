package com.jobassistance.repository;

import com.jobassistance.entity.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 訓練セッションリポジトリ
 * 訓練セッションエンティティのデータアクセスを提供
 */
@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {

    /**
     * セッションIDで訓練セッションを検索する
     *
     * @param sessionId セッションID
     * @return 該当する訓練セッション
     */
    Optional<TrainingSession> findBySessionId(String sessionId);

    /**
     * 就労者IDで訓練セッションを検索する
     *
     * @param workerId 就労者ID
     * @return 該当する訓練セッションのリスト
     */
    List<TrainingSession> findByWorkerId(Long workerId);

    /**
     * 訓練メニューIDで訓練セッションを検索する
     *
     * @param trainingMenuId 訓練メニューID
     * @return 該当する訓練セッションのリスト
     */
    List<TrainingSession> findByTrainingMenuId(Long trainingMenuId);
}
