package com.jobassistance.repository;

import com.jobassistance.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 就労者リポジトリ
 */
@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {
    
    Optional<Worker> findByEmail(String email);
    
    List<Worker> findByNameContainingIgnoreCase(String name);
    
    List<Worker> findByNationality(String nationality);
    
    List<Worker> findByCurrentStatus(String status);
}

