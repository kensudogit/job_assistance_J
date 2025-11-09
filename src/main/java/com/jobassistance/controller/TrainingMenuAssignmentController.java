package com.jobassistance.controller;

import com.jobassistance.entity.TrainingMenuAssignment;
import com.jobassistance.repository.TrainingMenuAssignmentRepository;
import com.jobassistance.repository.WorkerRepository;
import com.jobassistance.repository.TrainingMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 訓練メニュー割り当て管理コントローラー
 */
@RestController
@RequestMapping("/api/workers/{workerId}/training-menu-assignments")
public class TrainingMenuAssignmentController {

    @Autowired
    private TrainingMenuAssignmentRepository assignmentRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private TrainingMenuRepository trainingMenuRepository;

    /**
     * 訓練メニュー割り当て一覧取得
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAssignmentList(@PathVariable Long workerId) {
        try {
            if (!workerRepository.existsById(workerId)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            List<TrainingMenuAssignment> assignments = assignmentRepository.findByWorkerId(workerId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", assignments);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 訓練メニュー割り当て登録
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createAssignment(@PathVariable Long workerId, @RequestBody TrainingMenuAssignment assignment) {
        try {
            Optional<com.jobassistance.entity.Worker> worker = workerRepository.findById(workerId);
            if (!worker.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            if (assignment.getTrainingMenu() != null && assignment.getTrainingMenu().getId() != null) {
                Optional<com.jobassistance.entity.TrainingMenu> trainingMenu = trainingMenuRepository.findById(assignment.getTrainingMenu().getId());
                if (!trainingMenu.isPresent()) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", false);
                    response.put("error", "Training menu not found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
                assignment.setTrainingMenu(trainingMenu.get());
            }

            assignment.setWorker(worker.get());
            TrainingMenuAssignment savedAssignment = assignmentRepository.save(assignment);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", savedAssignment);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 訓練メニュー割り当て詳細取得
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getAssignment(@PathVariable Long workerId, @PathVariable Long id) {
        try {
            Optional<TrainingMenuAssignment> assignment = assignmentRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (assignment.isPresent() && assignment.get().getWorker().getId().equals(workerId)) {
                response.put("success", true);
                response.put("data", assignment.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Training menu assignment not found");
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
     * 訓練メニュー割り当て更新
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateAssignment(@PathVariable Long workerId, @PathVariable Long id, @RequestBody TrainingMenuAssignment assignment) {
        try {
            Optional<TrainingMenuAssignment> existingAssignment = assignmentRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (existingAssignment.isPresent() && existingAssignment.get().getWorker().getId().equals(workerId)) {
                assignment.setId(id);
                assignment.setWorker(existingAssignment.get().getWorker());
                if (assignment.getTrainingMenu() != null && assignment.getTrainingMenu().getId() != null) {
                    Optional<com.jobassistance.entity.TrainingMenu> trainingMenu = trainingMenuRepository.findById(assignment.getTrainingMenu().getId());
                    if (trainingMenu.isPresent()) {
                        assignment.setTrainingMenu(trainingMenu.get());
                    }
                } else {
                    assignment.setTrainingMenu(existingAssignment.get().getTrainingMenu());
                }
                TrainingMenuAssignment updatedAssignment = assignmentRepository.save(assignment);
                response.put("success", true);
                response.put("data", updatedAssignment);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Training menu assignment not found");
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
     * 訓練メニュー割り当て削除
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteAssignment(@PathVariable Long workerId, @PathVariable Long id) {
        try {
            Optional<TrainingMenuAssignment> assignment = assignmentRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (assignment.isPresent() && assignment.get().getWorker().getId().equals(workerId)) {
                assignmentRepository.deleteById(id);
                response.put("success", true);
                response.put("message", "Training menu assignment deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Training menu assignment not found");
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

