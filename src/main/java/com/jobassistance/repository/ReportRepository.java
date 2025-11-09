package com.jobassistance.repository;

import com.jobassistance.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * レポートリポジトリ
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    
    List<Report> findByWorkerId(Long workerId);
    
    List<Report> findByWorkerIdIsNull();
    
    List<Report> findByReportType(String reportType);
}

