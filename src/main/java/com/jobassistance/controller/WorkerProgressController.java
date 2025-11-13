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

    /** 就労者進捗リポジトリ */
    @Autowired
    private WorkerProgressRepository progressRepository;

    /** 就労者リポジトリ */
    @Autowired
    private WorkerRepository workerRepository;

    /**
     * 就労者の進捗一覧を取得する
     *
     * @param workerId 就労者ID
     * @return 進捗一覧を含むレスポンス
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
     * 新しい進捗記録を登録する
     *
     * @param workerId 就労者ID
     * @param progress 進捗情報
     * @return 作成された進捗記録を含むレスポンス
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createProgress(@PathVariable Long workerId,
            @RequestBody WorkerProgress progress) {
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
     * 進捗詳細を取得する
     *
     * @param workerId 就労者ID
     * @param progressId 進捗ID
     * @return 進捗詳細を含むレスポンス
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
     * 進捗情報を更新する
     *
     * @param workerId 就労者ID
     * @param progressId 進捗ID
     * @param progress 更新する進捗情報
     * @return 更新された進捗記録を含むレスポンス
     */
    @PutMapping("/{progressId}")
    public ResponseEntity<Map<String, Object>> updateProgress(@PathVariable Long workerId,
            @PathVariable Long progressId, @RequestBody WorkerProgress progress) {
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
     * 進捗記録を削除する
     *
     * @param workerId 就労者ID
     * @param progressId 進捗ID
     * @return 削除結果を含むレスポンス
     */
    @DeleteMapping("/{progressId}")
    public ResponseEntity<Map<String, Object>> deleteProgress(@PathVariable Long workerId,
            @PathVariable Long progressId) {
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
