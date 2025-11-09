package com.jobassistance.service;

import com.jobassistance.entity.TrainingSession;
import com.jobassistance.repository.TrainingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 訓練セッションサービスクラス
 */
@Service
@Transactional
public class TrainingSessionService {

    @Autowired
    private TrainingSessionRepository trainingSessionRepository;

    /**
     * 訓練セッション一覧取得
     */
    public List<TrainingSession> getAllTrainingSessions() {
        return trainingSessionRepository.findAll();
    }

    /**
     * 就労者IDで検索
     */
    public List<TrainingSession> getTrainingSessionsByWorkerId(Long workerId) {
        return trainingSessionRepository.findByWorkerId(workerId);
    }

    /**
     * 訓練メニューIDで検索
     */
    public List<TrainingSession> getTrainingSessionsByTrainingMenuId(Long trainingMenuId) {
        return trainingSessionRepository.findByTrainingMenuId(trainingMenuId);
    }

    /**
     * セッションIDで検索
     */
    public Optional<TrainingSession> getTrainingSessionBySessionId(String sessionId) {
        return trainingSessionRepository.findBySessionId(sessionId);
    }

    /**
     * 訓練セッション登録
     */
    public TrainingSession createTrainingSession(TrainingSession session) {
        return trainingSessionRepository.save(session);
    }

    /**
     * 訓練セッション詳細取得
     */
    public Optional<TrainingSession> getTrainingSessionById(Long id) {
        return trainingSessionRepository.findById(id);
    }

    /**
     * 訓練セッション更新
     */
    public TrainingSession updateTrainingSession(Long id, TrainingSession session) {
        session.setId(id);
        return trainingSessionRepository.save(session);
    }

    /**
     * 訓練セッション削除
     */
    public void deleteTrainingSession(Long id) {
        trainingSessionRepository.deleteById(id);
    }
}

