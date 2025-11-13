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

    /**
     * 就労者IDでキャリア目標を検索する
     * 
     * @param workerId 就労者ID
     * @return 該当するキャリア目標のリスト
     */
    List<CareerGoal> findByWorkerId(Long workerId);
}
