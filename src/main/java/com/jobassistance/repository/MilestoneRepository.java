package com.jobassistance.repository;

import com.jobassistance.entity.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * マイルストーンリポジトリ
 */
@Repository
public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
    
    List<Milestone> findByWorkerId(Long workerId);
}

