package com.jobassistance.controller;

import com.jobassistance.entity.Notification;
import com.jobassistance.repository.NotificationRepository;
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
 * 通知管理コントローラー
 */
@RestController
@RequestMapping("/api")
public class NotificationController {

    /** 通知リポジトリ */
    @Autowired
    private NotificationRepository notificationRepository;

    /** 就労者リポジトリ */
    @Autowired
    private WorkerRepository workerRepository;

    /**
     * 全員向け通知一覧を取得する
     *
     * @return 通知一覧を含むレスポンス
     */
    @GetMapping("/notifications")
    public ResponseEntity<Map<String, Object>> getAllNotifications() {
        try {
            List<Notification> notifications = notificationRepository.findByWorkerIdIsNull();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", notifications);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 全員向け通知を登録する
     *
     * @param notification 通知情報
     * @return 作成された通知を含むレスポンス
     */
    @PostMapping("/notifications")
    public ResponseEntity<Map<String, Object>> createNotification(@RequestBody Notification notification) {
        try {
            notification.setWorker(null);
            Notification savedNotification = notificationRepository.save(notification);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", savedNotification);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 就労者向け通知一覧を取得する
     *
     * @param workerId 就労者ID
     * @return 通知一覧を含むレスポンス
     */
    @GetMapping("/workers/{workerId}/notifications")
    public ResponseEntity<Map<String, Object>> getWorkerNotifications(@PathVariable Long workerId) {
        try {
            if (!workerRepository.existsById(workerId)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            List<Notification> notifications = notificationRepository.findByWorkerId(workerId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", notifications);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 就労者向け通知を登録する
     *
     * @param workerId 就労者ID
     * @param notification 通知情報
     * @return 作成された通知を含むレスポンス
     */
    @PostMapping("/workers/{workerId}/notifications")
    public ResponseEntity<Map<String, Object>> createWorkerNotification(@PathVariable Long workerId,
            @RequestBody Notification notification) {
        try {
            Optional<com.jobassistance.entity.Worker> worker = workerRepository.findById(workerId);
            if (!worker.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            notification.setWorker(worker.get());
            Notification savedNotification = notificationRepository.save(notification);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", savedNotification);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
