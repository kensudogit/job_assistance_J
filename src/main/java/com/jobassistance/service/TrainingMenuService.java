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

    /** 訓練メニューリポジトリ */
    @Autowired
    private TrainingMenuRepository trainingMenuRepository;

    /**
     * すべての訓練メニュー一覧を取得する
     * 
     * @return 訓練メニューのリスト
     */
    public List<TrainingMenu> getAllTrainingMenus() {
        return trainingMenuRepository.findAll();
    }

    /**
     * 有効な訓練メニュー一覧を取得する
     * 
     * @return 有効な訓練メニューのリスト
     */
    public List<TrainingMenu> getActiveTrainingMenus() {
        return trainingMenuRepository.findByIsActiveTrue();
    }

    /**
     * 難易度で訓練メニューを検索する
     * 
     * @param difficultyLevel 難易度
     * @return 該当する訓練メニューのリスト
     */
    public List<TrainingMenu> searchByDifficultyLevel(String difficultyLevel) {
        return trainingMenuRepository.findByDifficultyLevel(difficultyLevel);
    }

    /**
     * 新しい訓練メニューを登録する
     * 
     * @param menu 訓練メニュー情報
     * @return 作成された訓練メニュー
     */
    public TrainingMenu createTrainingMenu(TrainingMenu menu) {
        return trainingMenuRepository.save(menu);
    }

    /**
     * 指定されたIDの訓練メニュー詳細を取得する
     * 
     * @param id 訓練メニューID
     * @return 訓練メニュー（存在しない場合は空）
     */
    public Optional<TrainingMenu> getTrainingMenuById(Long id) {
        return trainingMenuRepository.findById(id);
    }

    /**
     * 訓練メニュー情報を更新する
     * 
     * @param id 訓練メニューID
     * @param menu 更新する訓練メニュー情報
     * @return 更新された訓練メニュー
     */
    public TrainingMenu updateTrainingMenu(Long id, TrainingMenu menu) {
        menu.setId(id);
        return trainingMenuRepository.save(menu);
    }

    /**
     * 訓練メニューを削除する
     * 
     * @param id 訓練メニューID
     */
    public void deleteTrainingMenu(Long id) {
        trainingMenuRepository.deleteById(id);
    }
}

