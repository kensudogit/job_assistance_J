package com.jobassistance.repository;

import com.jobassistance.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * メッセージリポジトリ
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    
    List<Message> findByWorkerId(Long workerId);
    
    List<Message> findByWorkerIdAndIsReadFalse(Long workerId);
    
    List<Message> findByRecipient(String recipient);
}

