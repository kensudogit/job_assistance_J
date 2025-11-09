package com.jobassistance.controller;

import com.jobassistance.entity.CalendarEvent;
import com.jobassistance.repository.CalendarEventRepository;
import com.jobassistance.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * カレンダーイベント管理コントローラー
 */
@RestController
@RequestMapping("/api")
public class CalendarEventController {

    @Autowired
    private CalendarEventRepository calendarEventRepository;

    @Autowired
    private WorkerRepository workerRepository;

    /**
     * 全員向けカレンダーイベント一覧取得
     */
    @GetMapping("/calendar")
    public ResponseEntity<Map<String, Object>> getAllCalendarEvents() {
        try {
            List<CalendarEvent> events = calendarEventRepository.findByWorkerIdIsNull();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", events);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 全員向けカレンダーイベント登録
     */
    @PostMapping("/calendar")
    public ResponseEntity<Map<String, Object>> createCalendarEvent(@RequestBody CalendarEvent event) {
        try {
            event.setWorker(null);
            CalendarEvent savedEvent = calendarEventRepository.save(event);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", savedEvent);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 就労者向けカレンダーイベント一覧取得
     */
    @GetMapping("/workers/{workerId}/calendar")
    public ResponseEntity<Map<String, Object>> getWorkerCalendarEvents(@PathVariable Long workerId) {
        try {
            if (!workerRepository.existsById(workerId)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            List<CalendarEvent> events = calendarEventRepository.findByWorkerId(workerId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", events);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 就労者向けカレンダーイベント登録
     */
    @PostMapping("/workers/{workerId}/calendar")
    public ResponseEntity<Map<String, Object>> createWorkerCalendarEvent(@PathVariable Long workerId, @RequestBody CalendarEvent event) {
        try {
            Optional<com.jobassistance.entity.Worker> worker = workerRepository.findById(workerId);
            if (!worker.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            event.setWorker(worker.get());
            CalendarEvent savedEvent = calendarEventRepository.save(event);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", savedEvent);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

