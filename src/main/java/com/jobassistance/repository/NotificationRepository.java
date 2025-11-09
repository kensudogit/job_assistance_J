package com.jobassistance.repository;

import com.jobassistance.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 通知リポジトリ
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    List<Notification> findByWorkerId(Long workerId);
    
    List<Notification> findByWorkerIdIsNull();
    
    List<Notification> findByWorkerIdAndIsReadFalse(Long workerId);
}

