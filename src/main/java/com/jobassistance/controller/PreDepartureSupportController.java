package com.jobassistance.controller;

import com.jobassistance.entity.PreDepartureSupport;
import com.jobassistance.repository.PreDepartureSupportRepository;
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
 * 来日前支援管理コントローラー
 */
@RestController
@RequestMapping("/api/workers/{workerId}/pre-departure-support")
public class PreDepartureSupportController {

    @Autowired
    private PreDepartureSupportRepository preDepartureSupportRepository;

    @Autowired
    private WorkerRepository workerRepository;

    /**
     * 来日前支援一覧取得
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getPreDepartureSupportList(@PathVariable Long workerId) {
        try {
            if (!workerRepository.existsById(workerId)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            List<PreDepartureSupport> supports = preDepartureSupportRepository.findByWorkerId(workerId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", supports);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 来日前支援登録
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createPreDepartureSupport(@PathVariable Long workerId,
            @RequestBody PreDepartureSupport support) {
        try {
            Optional<com.jobassistance.entity.Worker> worker = workerRepository.findById(workerId);
            if (!worker.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            support.setWorker(worker.get());
            PreDepartureSupport savedSupport = preDepartureSupportRepository.save(support);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", savedSupport);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 来日前支援詳細取得
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getPreDepartureSupport(@PathVariable Long workerId,
            @PathVariable Long id) {
        try {
            Optional<PreDepartureSupport> support = preDepartureSupportRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (support.isPresent() && support.get().getWorker().getId().equals(workerId)) {
                response.put("success", true);
                response.put("data", support.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Pre-departure support not found");
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
     * 来日前支援更新
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updatePreDepartureSupport(@PathVariable Long workerId,
            @PathVariable Long id, @RequestBody PreDepartureSupport support) {
        try {
            Optional<PreDepartureSupport> existingSupport = preDepartureSupportRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (existingSupport.isPresent() && existingSupport.get().getWorker().getId().equals(workerId)) {
                support.setId(id);
                support.setWorker(existingSupport.get().getWorker());
                PreDepartureSupport updatedSupport = preDepartureSupportRepository.save(support);
                response.put("success", true);
                response.put("data", updatedSupport);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Pre-departure support not found");
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
     * 来日前支援削除
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletePreDepartureSupport(@PathVariable Long workerId,
            @PathVariable Long id) {
        try {
            Optional<PreDepartureSupport> support = preDepartureSupportRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (support.isPresent() && support.get().getWorker().getId().equals(workerId)) {
                preDepartureSupportRepository.deleteById(id);
                response.put("success", true);
                response.put("message", "Pre-departure support deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Pre-departure support not found");
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
