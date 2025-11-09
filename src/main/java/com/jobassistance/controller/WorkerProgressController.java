package com.jobassistance.controller;

import com.jobassistance.entity.WorkerProgress;
import com.jobassistance.repository.WorkerProgressRepository;
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
 * 就労者進捗管理コントローラー
 */
@RestController
@RequestMapping("/api/workers/{workerId}/progress")
public class WorkerProgressController {

    @Autowired
    private WorkerProgressRepository progressRepository;

    @Autowired
    private WorkerRepository workerRepository;

    /**
     * 進捗一覧取得
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getProgressList(@PathVariable Long workerId) {
        try {
            if (!workerRepository.existsById(workerId)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            List<WorkerProgress> progressList = progressRepository.findByWorkerId(workerId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", progressList);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 進捗登録
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createProgress(@PathVariable Long workerId, @RequestBody WorkerProgress progress) {
        try {
            Optional<com.jobassistance.entity.Worker> worker = workerRepository.findById(workerId);
            if (!worker.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            progress.setWorker(worker.get());
            WorkerProgress savedProgress = progressRepository.save(progress);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", savedProgress);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 進捗詳細取得
     */
    @GetMapping("/{progressId}")
    public ResponseEntity<Map<String, Object>> getProgress(@PathVariable Long workerId, @PathVariable Long progressId) {
        try {
            Optional<WorkerProgress> progress = progressRepository.findById(progressId);
            Map<String, Object> response = new HashMap<>();
            if (progress.isPresent() && progress.get().getWorker().getId().equals(workerId)) {
                response.put("success", true);
                response.put("data", progress.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Progress not found");
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
     * 進捗更新
     */
    @PutMapping("/{progressId}")
    public ResponseEntity<Map<String, Object>> updateProgress(@PathVariable Long workerId, @PathVariable Long progressId, @RequestBody WorkerProgress progress) {
        try {
            Optional<WorkerProgress> existingProgress = progressRepository.findById(progressId);
            Map<String, Object> response = new HashMap<>();
            if (existingProgress.isPresent() && existingProgress.get().getWorker().getId().equals(workerId)) {
                progress.setId(progressId);
                progress.setWorker(existingProgress.get().getWorker());
                WorkerProgress updatedProgress = progressRepository.save(progress);
                response.put("success", true);
                response.put("data", updatedProgress);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Progress not found");
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
     * 進捗削除
     */
    @DeleteMapping("/{progressId}")
    public ResponseEntity<Map<String, Object>> deleteProgress(@PathVariable Long workerId, @PathVariable Long progressId) {
        try {
            Optional<WorkerProgress> progress = progressRepository.findById(progressId);
            Map<String, Object> response = new HashMap<>();
            if (progress.isPresent() && progress.get().getWorker().getId().equals(workerId)) {
                progressRepository.deleteById(progressId);
                response.put("success", true);
                response.put("message", "Progress deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Progress not found");
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

