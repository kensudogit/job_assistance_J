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

/**
 * 就労者向け訓練セッション管理コントローラー
 */
@RestController
@RequestMapping("/api/workers/{workerId}/training-sessions")
public class WorkerTrainingSessionController {

    /** 訓練セッションリポジトリ */
    @Autowired
    private TrainingSessionRepository trainingSessionRepository;

    /** 就労者リポジトリ */
    @Autowired
    private WorkerRepository workerRepository;

    /**
     * 就労者の訓練セッション一覧を取得する
     *
     * @param workerId 就労者ID
     * @return 訓練セッション一覧を含むレスポンス
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getWorkerTrainingSessions(@PathVariable Long workerId) {
        try {
            if (!workerRepository.existsById(workerId)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            List<TrainingSession> sessions = trainingSessionRepository.findByWorkerId(workerId);
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
}
