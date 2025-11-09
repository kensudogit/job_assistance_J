package com.jobassistance.controller;

import com.jobassistance.entity.Evaluation;
import com.jobassistance.repository.EvaluationRepository;
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
 * 評価管理コントローラー
 */
@RestController
@RequestMapping("/api/workers/{workerId}/evaluations")
public class EvaluationController {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private WorkerRepository workerRepository;

    /**
     * 評価一覧取得
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getEvaluationList(@PathVariable Long workerId) {
        try {
            if (!workerRepository.existsById(workerId)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            List<Evaluation> evaluations = evaluationRepository.findByWorkerIdOrderByEvaluationDateDesc(workerId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", evaluations);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 評価登録
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createEvaluation(@PathVariable Long workerId, @RequestBody Evaluation evaluation) {
        try {
            Optional<com.jobassistance.entity.Worker> worker = workerRepository.findById(workerId);
            if (!worker.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            evaluation.setWorker(worker.get());
            Evaluation savedEvaluation = evaluationRepository.save(evaluation);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", savedEvaluation);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 評価詳細取得
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getEvaluation(@PathVariable Long workerId, @PathVariable Long id) {
        try {
            Optional<Evaluation> evaluation = evaluationRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (evaluation.isPresent() && evaluation.get().getWorker().getId().equals(workerId)) {
                response.put("success", true);
                response.put("data", evaluation.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Evaluation not found");
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
     * 評価更新
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateEvaluation(@PathVariable Long workerId, @PathVariable Long id, @RequestBody Evaluation evaluation) {
        try {
            Optional<Evaluation> existingEvaluation = evaluationRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (existingEvaluation.isPresent() && existingEvaluation.get().getWorker().getId().equals(workerId)) {
                evaluation.setId(id);
                evaluation.setWorker(existingEvaluation.get().getWorker());
                Evaluation updatedEvaluation = evaluationRepository.save(evaluation);
                response.put("success", true);
                response.put("data", updatedEvaluation);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Evaluation not found");
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
     * 評価削除
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteEvaluation(@PathVariable Long workerId, @PathVariable Long id) {
        try {
            Optional<Evaluation> evaluation = evaluationRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (evaluation.isPresent() && evaluation.get().getWorker().getId().equals(workerId)) {
                evaluationRepository.deleteById(id);
                response.put("success", true);
                response.put("message", "Evaluation deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Evaluation not found");
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

