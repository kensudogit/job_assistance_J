package com.jobassistance.repository;

import com.jobassistance.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 研修リポジトリ
 */
@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

    List<Training> findByStatus(String status);

    List<Training> findByTrainingType(String trainingType);
}
