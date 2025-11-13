package com.jobassistance.repository;

import com.jobassistance.entity.PreDepartureSupport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 来日前支援リポジトリ
 */
@Repository
public interface PreDepartureSupportRepository extends JpaRepository<PreDepartureSupport, Long> {

    List<PreDepartureSupport> findByWorkerId(Long workerId);

    List<PreDepartureSupport> findByWorkerIdAndStatus(Long workerId, String status);
}
