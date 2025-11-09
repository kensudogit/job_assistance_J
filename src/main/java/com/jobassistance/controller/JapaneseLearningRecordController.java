package com.jobassistance.controller;

import com.jobassistance.entity.JapaneseLearningRecord;
import com.jobassistance.repository.JapaneseLearningRecordRepository;
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
 * 日本語学習記録管理コントローラー
 */
@RestController
@RequestMapping("/api/workers/{workerId}/japanese-learning")
public class JapaneseLearningRecordController {

    @Autowired
    private JapaneseLearningRecordRepository learningRecordRepository;

    @Autowired
    private WorkerRepository workerRepository;

    /**
     * 日本語学習記録一覧取得
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getLearningRecordList(@PathVariable Long workerId) {
        try {
            if (!workerRepository.existsById(workerId)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            List<JapaneseLearningRecord> records = learningRecordRepository.findByWorkerIdOrderByLearningDateDesc(workerId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", records);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 日本語学習記録登録
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createLearningRecord(@PathVariable Long workerId, @RequestBody JapaneseLearningRecord record) {
        try {
            Optional<com.jobassistance.entity.Worker> worker = workerRepository.findById(workerId);
            if (!worker.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            record.setWorker(worker.get());
            JapaneseLearningRecord savedRecord = learningRecordRepository.save(record);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", savedRecord);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 日本語学習記録詳細取得
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getLearningRecord(@PathVariable Long workerId, @PathVariable Long id) {
        try {
            Optional<JapaneseLearningRecord> record = learningRecordRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (record.isPresent() && record.get().getWorker().getId().equals(workerId)) {
                response.put("success", true);
                response.put("data", record.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Japanese learning record not found");
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
     * 日本語学習記録更新
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateLearningRecord(@PathVariable Long workerId, @PathVariable Long id, @RequestBody JapaneseLearningRecord record) {
        try {
            Optional<JapaneseLearningRecord> existingRecord = learningRecordRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (existingRecord.isPresent() && existingRecord.get().getWorker().getId().equals(workerId)) {
                record.setId(id);
                record.setWorker(existingRecord.get().getWorker());
                JapaneseLearningRecord updatedRecord = learningRecordRepository.save(record);
                response.put("success", true);
                response.put("data", updatedRecord);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Japanese learning record not found");
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
     * 日本語学習記録削除
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteLearningRecord(@PathVariable Long workerId, @PathVariable Long id) {
        try {
            Optional<JapaneseLearningRecord> record = learningRecordRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (record.isPresent() && record.get().getWorker().getId().equals(workerId)) {
                learningRecordRepository.deleteById(id);
                response.put("success", true);
                response.put("message", "Japanese learning record deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Japanese learning record not found");
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

