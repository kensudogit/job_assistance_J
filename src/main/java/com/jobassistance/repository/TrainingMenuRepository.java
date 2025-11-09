package com.jobassistance.repository;

import com.jobassistance.entity.TrainingMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 訓練メニューリポジトリ
 */
@Repository
public interface TrainingMenuRepository extends JpaRepository<TrainingMenu, Long> {
    
    List<TrainingMenu> findByIsActiveTrue();
    
    List<TrainingMenu> findByDifficultyLevel(String difficultyLevel);
}

