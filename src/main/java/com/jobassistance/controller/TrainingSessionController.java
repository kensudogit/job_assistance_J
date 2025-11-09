package com.jobassistance.controller;

import com.jobassistance.entity.TrainingSession;
import com.jobassistance.repository.TrainingSessionRepository;
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
 * 訓練セッション管理コントローラー
 */
@RestController
@RequestMapping("/api/training-sessions")
public class TrainingSessionController {

    @Autowired
    private TrainingSessionRepository trainingSessionRepository;

    @Autowired
    private WorkerRepository workerRepository;

    /**
     * 訓練セッション一覧取得
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllTrainingSessions() {
        try {
            List<TrainingSession> sessions = trainingSessionRepository.findAll();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", sessions);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 訓練セッション登録
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createTrainingSession(@RequestBody TrainingSession session) {
        try {
            // worker_idが指定されている場合、存在確認
            if (session.getWorker() != null && session.getWorker().getId() != null) {
                if (!workerRepository.existsById(session.getWorker().getId())) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("error", "Worker not found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
            }

            TrainingSession savedSession = trainingSessionRepository.save(session);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", savedSession);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 訓練セッション詳細取得
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getTrainingSession(@PathVariable Long id) {
        try {
            Optional<TrainingSession> session = trainingSessionRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (session.isPresent()) {
                response.put("success", true);
                response.put("data", session.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Training session not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 訓練セッション更新
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateTrainingSession(@PathVariable Long id, @RequestBody TrainingSession session) {
        try {
            Optional<TrainingSession> existingSession = trainingSessionRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (existingSession.isPresent()) {
                session.setId(id);
                TrainingSession updatedSession = trainingSessionRepository.save(session);
                response.put("success", true);
                response.put("data", updatedSession);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Training session not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 訓練セッション削除
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteTrainingSession(@PathVariable Long id) {
        try {
            Optional<TrainingSession> session = trainingSessionRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (session.isPresent()) {
                trainingSessionRepository.deleteById(id);
                response.put("success", true);
                response.put("message", "Training session deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Training session not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

