package com.jobassistance.repository;

import com.jobassistance.entity.TrainingMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 訓練メニューリポジトリ
 * 訓練メニューエンティティのデータアクセスを提供
 */
@Repository
public interface TrainingMenuRepository extends JpaRepository<TrainingMenu, Long> {
    
    /**
     * アクティブな訓練メニュー一覧を取得する
     *
     * @return アクティブな訓練メニューのリスト
     */
    List<TrainingMenu> findByIsActiveTrue();
    
    /**
     * 難易度レベルで訓練メニューを検索する
     *
     * @param difficultyLevel 難易度レベル
     * @return 該当する訓練メニューのリスト
     */
    List<TrainingMenu> findByDifficultyLevel(String difficultyLevel);
}

