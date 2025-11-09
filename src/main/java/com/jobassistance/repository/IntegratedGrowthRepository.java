package com.jobassistance.repository;

import com.jobassistance.entity.IntegratedGrowth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 統合成長リポジトリ
 */
@Repository
public interface IntegratedGrowthRepository extends JpaRepository<IntegratedGrowth, Long> {
    
    List<IntegratedGrowth> findByWorkerId(Long workerId);
}

