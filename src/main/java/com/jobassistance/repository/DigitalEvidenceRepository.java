package com.jobassistance.repository;

import com.jobassistance.entity.DigitalEvidence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * デジタル証拠リポジトリ
 */
@Repository
public interface DigitalEvidenceRepository extends JpaRepository<DigitalEvidence, Long> {
    
    List<DigitalEvidence> findByWorkerId(Long workerId);
}

