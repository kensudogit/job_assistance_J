package com.jobassistance.repository;

import com.jobassistance.entity.JapaneseProficiency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 日本語能力リポジトリ
 */
@Repository
public interface JapaneseProficiencyRepository extends JpaRepository<JapaneseProficiency, Long> {
    
    List<JapaneseProficiency> findByWorkerId(Long workerId);
    
    List<JapaneseProficiency> findByWorkerIdAndTestType(Long workerId, String testType);
}

