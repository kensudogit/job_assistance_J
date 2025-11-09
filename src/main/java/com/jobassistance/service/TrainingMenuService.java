package com.jobassistance.service;

import com.jobassistance.entity.TrainingMenu;
import com.jobassistance.repository.TrainingMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 訓練メニューサービスクラス
 */
@Service
@Transactional
public class TrainingMenuService {

    @Autowired
    private TrainingMenuRepository trainingMenuRepository;

    /**
     * 訓練メニュー一覧取得
     */
    public List<TrainingMenu> getAllTrainingMenus() {
        return trainingMenuRepository.findAll();
    }

    /**
     * 有効な訓練メニュー一覧取得
     */
    public List<TrainingMenu> getActiveTrainingMenus() {
        return trainingMenuRepository.findByIsActiveTrue();
    }

    /**
     * 難易度で検索
     */
    public List<TrainingMenu> searchByDifficultyLevel(String difficultyLevel) {
        return trainingMenuRepository.findByDifficultyLevel(difficultyLevel);
    }

    /**
     * 訓練メニュー登録
     */
    public TrainingMenu createTrainingMenu(TrainingMenu menu) {
        return trainingMenuRepository.save(menu);
    }

    /**
     * 訓練メニュー詳細取得
     */
    public Optional<TrainingMenu> getTrainingMenuById(Long id) {
        return trainingMenuRepository.findById(id);
    }

    /**
     * 訓練メニュー更新
     */
    public TrainingMenu updateTrainingMenu(Long id, TrainingMenu menu) {
        menu.setId(id);
        return trainingMenuRepository.save(menu);
    }

    /**
     * 訓練メニュー削除
     */
    public void deleteTrainingMenu(Long id) {
        trainingMenuRepository.deleteById(id);
    }
}

