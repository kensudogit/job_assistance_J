package com.jobassistance.controller;

import com.jobassistance.entity.Worker;
import com.jobassistance.repository.WorkerRepository;
import com.jobassistance.repository.TrainingSessionRepository;
import com.jobassistance.repository.TrainingMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理者用サマリーコントローラー
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private TrainingSessionRepository trainingSessionRepository;

    @Autowired
    private TrainingMenuRepository trainingMenuRepository;

    /**
     * 管理者用サマリー取得
     */
    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getAdminSummary() {
        try {
            long totalWorkers = workerRepository.count();
            long totalTrainingSessions = trainingSessionRepository.count();
            long totalTrainingMenus = trainingMenuRepository.count();
            long activeTrainingMenus = trainingMenuRepository.findByIsActiveTrue().size();

            Map<String, Object> summary = new HashMap<>();
            summary.put("totalWorkers", totalWorkers);
            summary.put("totalTrainingSessions", totalTrainingSessions);
            summary.put("totalTrainingMenus", totalTrainingMenus);
            summary.put("activeTrainingMenus", activeTrainingMenus);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", summary);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

