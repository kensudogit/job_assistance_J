package com.jobassistance.repository;

import com.jobassistance.entity.TrainingMenuAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 訓練メニュー割り当てリポジトリ
 */
@Repository
public interface TrainingMenuAssignmentRepository extends JpaRepository<TrainingMenuAssignment, Long> {
    
    List<TrainingMenuAssignment> findByWorkerId(Long workerId);
    
    List<TrainingMenuAssignment> findByTrainingMenuId(Long trainingMenuId);
    
    List<TrainingMenuAssignment> findByWorkerIdAndStatus(Long workerId, String status);
}

