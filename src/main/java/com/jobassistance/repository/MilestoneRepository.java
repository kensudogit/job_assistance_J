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

    /**
     * 就労者IDでマイルストーンを検索する
     * 
     * @param workerId 就労者ID
     * @return 該当するマイルストーンのリスト
     */
    List<Milestone> findByWorkerId(Long workerId);
}
