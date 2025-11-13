package com.jobassistance.repository;

import com.jobassistance.entity.WorkerProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 就労者進捗リポジトリ
 */
@Repository
public interface WorkerProgressRepository extends JpaRepository<WorkerProgress, Long> {

    List<WorkerProgress> findByWorkerId(Long workerId);

    List<WorkerProgress> findByWorkerIdAndProgressType(Long workerId, String progressType);
}
