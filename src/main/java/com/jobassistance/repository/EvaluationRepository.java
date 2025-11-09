package com.jobassistance.repository;

import com.jobassistance.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 評価リポジトリ
 */
@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    
    List<Evaluation> findByWorkerId(Long workerId);
    
    List<Evaluation> findByWorkerIdOrderByEvaluationDateDesc(Long workerId);
}

