package com.jobassistance.repository;

import com.jobassistance.entity.JapaneseLearningRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 日本語学習記録リポジトリ
 */
@Repository
public interface JapaneseLearningRecordRepository extends JpaRepository<JapaneseLearningRecord, Long> {
    
    List<JapaneseLearningRecord> findByWorkerId(Long workerId);
    
    List<JapaneseLearningRecord> findByWorkerIdOrderByLearningDateDesc(Long workerId);
}

