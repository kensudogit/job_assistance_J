package com.jobassistance.repository;

import com.jobassistance.entity.ConstructionSimulatorTraining;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 建設シミュレーター訓練リポジトリ
 */
@Repository
public interface ConstructionSimulatorTrainingRepository extends JpaRepository<ConstructionSimulatorTraining, Long> {
    
    List<ConstructionSimulatorTraining> findByWorkerId(Long workerId);
}

