package com.jobassistance.repository;

import com.jobassistance.entity.CareerGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * キャリア目標リポジトリ
 */
@Repository
public interface CareerGoalRepository extends JpaRepository<CareerGoal, Long> {
    
    List<CareerGoal> findByWorkerId(Long workerId);
}

