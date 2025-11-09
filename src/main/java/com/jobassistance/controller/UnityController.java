package com.jobassistance.controller;

import com.jobassistance.entity.TrainingSession;
import com.jobassistance.entity.TrainingMenu;
import com.jobassistance.entity.Worker;
import com.jobassistance.repository.TrainingSessionRepository;
import com.jobassistance.repository.TrainingMenuRepository;
import com.jobassistance.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Unity統合コントローラー
 */
@RestController
@RequestMapping("/api/unity")
public class UnityController {

    @Autowired
    private TrainingSessionRepository trainingSessionRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private TrainingMenuRepository trainingMenuRepository;

    /**
     * Unity訓練セッション作成
     */
    @PostMapping("/training-session")
    public ResponseEntity<Map<String, Object>> createTrainingSession(@RequestBody Map<String, Object> sessionData) {
        try {
            Long workerId = ((Number) sessionData.get("workerId")).longValue();
            Optional<Worker> worker = workerRepository.findById(workerId);
            if (!worker.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            // 訓練セッションを作成
            TrainingSession session = new TrainingSession();
            session.setWorker(worker.get());
            session.setSessionId(UUID.randomUUID().toString());
            session.setSessionStartTime(LocalDateTime.now());
            // trainingMenuIdが指定されている場合、TrainingMenuを設定
            if (sessionData.containsKey("menuId") && sessionData.get("menuId") != null) {
                Long menuId = ((Number) sessionData.get("menuId")).longValue();
                if (menuId > 0) {
                    Optional<TrainingMenu> trainingMenu = trainingMenuRepository.findById(menuId);
                    trainingMenu.ifPresent(session::setTrainingMenu);
                }
            }
            session.setStatus("開始");

            TrainingSession savedSession = trainingSessionRepository.save(session);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", Map.of(
                "sessionId", savedSession.getSessionId(),
                "sessionIdLong", savedSession.getId()
            ));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Unityコマンド処理
     */
    @PostMapping("/command")
    public ResponseEntity<Map<String, Object>> handleCommand(@RequestBody Map<String, Object> commandData) {
        try {
            String command = (String) commandData.get("command");
            String sessionId = (String) commandData.get("sessionId");

            if (command == null || sessionId == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Command and sessionId are required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // コマンド処理（簡易版）
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", Map.of(
                "command", command,
                "sessionId", sessionId,
                "status", "processed"
            ));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

