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

    /** 来日前支援リポジトリ */
    @Autowired
    private PreDepartureSupportRepository preDepartureSupportRepository;

    /** 就労者リポジトリ */
    @Autowired
    private WorkerRepository workerRepository;

    /**
     * 就労者の来日前支援一覧を取得する
     *
     * @param workerId 就労者ID
     * @return 来日前支援一覧を含むレスポンス
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
     * 新しい来日前支援を登録する
     *
     * @param workerId 就労者ID
     * @param support 来日前支援情報
     * @return 作成された来日前支援を含むレスポンス
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
     * 来日前支援詳細を取得する
     *
     * @param workerId 就労者ID
     * @param id 来日前支援ID
     * @return 来日前支援詳細を含むレスポンス
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
     * 来日前支援情報を更新する
     *
     * @param workerId 就労者ID
     * @param id 来日前支援ID
     * @param support 更新する来日前支援情報
     * @return 更新された来日前支援を含むレスポンス
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
     * 来日前支援を削除する
     *
     * @param workerId 就労者ID
     * @param id 来日前支援ID
     * @return 削除結果を含むレスポンス
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
