package com.jobassistance.repository;

import com.jobassistance.entity.CalendarEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * カレンダーイベントリポジトリ
 */
@Repository
public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Long> {
    
    List<CalendarEvent> findByWorkerId(Long workerId);
    
    List<CalendarEvent> findByWorkerIdIsNull();
    
    List<CalendarEvent> findByStartDatetimeBetween(LocalDateTime start, LocalDateTime end);
}

