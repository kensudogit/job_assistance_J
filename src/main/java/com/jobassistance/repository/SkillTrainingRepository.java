package com.jobassistance.repository;

import com.jobassistance.entity.SkillTraining;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 技能訓練リポジトリ
 */
@Repository
public interface SkillTrainingRepository extends JpaRepository<SkillTraining, Long> {
    
    List<SkillTraining> findByWorkerId(Long workerId);
    
    List<SkillTraining> findByWorkerIdAndSkillCategory(Long workerId, String skillCategory);
}

