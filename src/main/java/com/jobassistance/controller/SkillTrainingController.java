package com.jobassistance.controller;

import com.jobassistance.entity.SkillTraining;
import com.jobassistance.repository.SkillTrainingRepository;
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
 * 技能訓練管理コントローラー
 */
@RestController
@RequestMapping("/api/workers/{workerId}/skill-training")
public class SkillTrainingController {

    @Autowired
    private SkillTrainingRepository skillTrainingRepository;

    @Autowired
    private WorkerRepository workerRepository;

    /**
     * 技能訓練一覧取得
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getSkillTrainingList(@PathVariable Long workerId) {
        try {
            if (!workerRepository.existsById(workerId)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            List<SkillTraining> trainings = skillTrainingRepository.findByWorkerId(workerId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", trainings);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 技能訓練登録
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createSkillTraining(@PathVariable Long workerId,
            @RequestBody SkillTraining training) {
        try {
            Optional<com.jobassistance.entity.Worker> worker = workerRepository.findById(workerId);
            if (!worker.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            training.setWorker(worker.get());
            SkillTraining savedTraining = skillTrainingRepository.save(training);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", savedTraining);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 技能訓練詳細取得
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getSkillTraining(@PathVariable Long workerId, @PathVariable Long id) {
        try {
            Optional<SkillTraining> training = skillTrainingRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (training.isPresent() && training.get().getWorker().getId().equals(workerId)) {
                response.put("success", true);
                response.put("data", training.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Skill training not found");
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
     * 技能訓練更新
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateSkillTraining(@PathVariable Long workerId, @PathVariable Long id,
            @RequestBody SkillTraining training) {
        try {
            Optional<SkillTraining> existingTraining = skillTrainingRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (existingTraining.isPresent() && existingTraining.get().getWorker().getId().equals(workerId)) {
                training.setId(id);
                training.setWorker(existingTraining.get().getWorker());
                SkillTraining updatedTraining = skillTrainingRepository.save(training);
                response.put("success", true);
                response.put("data", updatedTraining);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Skill training not found");
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
     * 技能訓練削除
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteSkillTraining(@PathVariable Long workerId, @PathVariable Long id) {
        try {
            Optional<SkillTraining> training = skillTrainingRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (training.isPresent() && training.get().getWorker().getId().equals(workerId)) {
                skillTrainingRepository.deleteById(id);
                response.put("success", true);
                response.put("message", "Skill training deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Skill training not found");
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
