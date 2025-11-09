package com.jobassistance.controller;

import com.jobassistance.entity.Worker;
import com.jobassistance.repository.WorkerRepository;
import com.jobassistance.service.WorkerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 就労者管理コントローラー
 */
@RestController
@RequestMapping("/api/workers")
public class WorkerController {

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private WorkerService workerService;

    /**
     * 就労者一覧取得
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllWorkers() {
        try {
            List<Worker> workers = workerRepository.findAll();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", workers);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 就労者登録
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createWorker(@Valid @RequestBody Worker worker) {
        try {
            Worker savedWorker = workerRepository.save(worker);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", savedWorker);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 就労者詳細取得
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getWorker(@PathVariable Long id) {
        try {
            Optional<Worker> worker = workerRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (worker.isPresent()) {
                response.put("success", true);
                response.put("data", worker.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Worker not found");
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
     * 就労者更新
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateWorker(@PathVariable Long id, @Valid @RequestBody Worker worker) {
        try {
            Optional<Worker> existingWorker = workerRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (existingWorker.isPresent()) {
                worker.setId(id);
                Worker updatedWorker = workerRepository.save(worker);
                response.put("success", true);
                response.put("data", updatedWorker);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Worker not found");
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
     * 就労者削除
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteWorker(@PathVariable Long id) {
        try {
            Optional<Worker> worker = workerRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (worker.isPresent()) {
                workerRepository.deleteById(id);
                response.put("success", true);
                response.put("message", "Worker deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Worker not found");
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

