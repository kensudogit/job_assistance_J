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

    /** 訓練セッションリポジトリ */
    @Autowired
    private TrainingSessionRepository trainingSessionRepository;

    /**
     * すべての訓練セッション一覧を取得する
     * 
     * @return 訓練セッションのリスト
     */
    public List<TrainingSession> getAllTrainingSessions() {
        return trainingSessionRepository.findAll();
    }

    /**
     * 就労者IDで訓練セッションを検索する
     * 
     * @param workerId 就労者ID
     * @return 該当する訓練セッションのリスト
     */
    public List<TrainingSession> getTrainingSessionsByWorkerId(Long workerId) {
        return trainingSessionRepository.findByWorkerId(workerId);
    }

    /**
     * 訓練メニューIDで訓練セッションを検索する
     * 
     * @param trainingMenuId 訓練メニューID
     * @return 該当する訓練セッションのリスト
     */
    public List<TrainingSession> getTrainingSessionsByTrainingMenuId(Long trainingMenuId) {
        return trainingSessionRepository.findByTrainingMenuId(trainingMenuId);
    }

    /**
     * セッションIDで訓練セッションを検索する
     * 
     * @param sessionId セッションID
     * @return 訓練セッション（存在しない場合は空）
     */
    public Optional<TrainingSession> getTrainingSessionBySessionId(String sessionId) {
        return trainingSessionRepository.findBySessionId(sessionId);
    }

    /**
     * 新しい訓練セッションを登録する
     * 
     * @param session 訓練セッション情報
     * @return 作成された訓練セッション
     */
    public TrainingSession createTrainingSession(TrainingSession session) {
        return trainingSessionRepository.save(session);
    }

    /**
     * 指定されたIDの訓練セッション詳細を取得する
     * 
     * @param id 訓練セッションID
     * @return 訓練セッション（存在しない場合は空）
     */
    public Optional<TrainingSession> getTrainingSessionById(Long id) {
        return trainingSessionRepository.findById(id);
    }

    /**
     * 訓練セッション情報を更新する
     * 
     * @param id 訓練セッションID
     * @param session 更新する訓練セッション情報
     * @return 更新された訓練セッション
     */
    public TrainingSession updateTrainingSession(Long id, TrainingSession session) {
        session.setId(id);
        return trainingSessionRepository.save(session);
    }

    /**
     * 訓練セッションを削除する
     * 
     * @param id 訓練セッションID
     */
    public void deleteTrainingSession(Long id) {
        trainingSessionRepository.deleteById(id);
    }
}
