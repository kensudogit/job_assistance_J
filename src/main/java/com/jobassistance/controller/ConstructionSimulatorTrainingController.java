package com.jobassistance.controller;

import com.jobassistance.entity.ConstructionSimulatorTraining;
import com.jobassistance.entity.Worker;
import com.jobassistance.repository.ConstructionSimulatorTrainingRepository;
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
 * 建設シミュレーター訓練管理コントローラー
 */
@RestController
@RequestMapping("/api/workers/{workerId}/simulator-training")
public class ConstructionSimulatorTrainingController {

    @Autowired
    private ConstructionSimulatorTrainingRepository trainingRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getTrainings(@PathVariable Long workerId) {
        try {
            Optional<Worker> worker = workerRepository.findById(workerId);
            if (!worker.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            List<ConstructionSimulatorTraining> trainings = trainingRepository.findByWorkerId(workerId);
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

    @PostMapping
    public ResponseEntity<Map<String, Object>> createTraining(@PathVariable Long workerId, @RequestBody Map<String, Object> trainingData) {
        try {
            Optional<Worker> worker = workerRepository.findById(workerId);
            if (!worker.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            ConstructionSimulatorTraining training = new ConstructionSimulatorTraining();
            training.setWorker(worker.get());
            if (trainingData.get("trainingDate") != null) {
                training.setTrainingDate(java.time.LocalDate.parse((String) trainingData.get("trainingDate")));
            }
            training.setEquipmentType((String) trainingData.get("equipmentType"));
            training.setDifficultyLevel((String) trainingData.get("difficultyLevel"));
            if (trainingData.get("timeLimit") != null) {
                training.setTimeLimit(((Number) trainingData.get("timeLimit")).intValue());
            }
            if (trainingData.get("actualTime") != null) {
                training.setActualTime(((Number) trainingData.get("actualTime")).intValue());
            }
            if (trainingData.get("safetyScore") != null) {
                training.setSafetyScore(((Number) trainingData.get("safetyScore")).doubleValue());
            }
            if (trainingData.get("errorCount") != null) {
                training.setErrorCount(((Number) trainingData.get("errorCount")).intValue());
            }
            if (trainingData.get("procedureCompliance") != null) {
                training.setProcedureCompliance(((Number) trainingData.get("procedureCompliance")).doubleValue());
            }
            if (trainingData.get("achievementRate") != null) {
                training.setAchievementRate(((Number) trainingData.get("achievementRate")).doubleValue());
            }
            training.setSessionData((String) trainingData.get("sessionData"));
            if (trainingData.get("status") != null) {
                training.setStatus((String) trainingData.get("status"));
            }
            training.setFeedback((String) trainingData.get("feedback"));
            training.setNotes((String) trainingData.get("notes"));

            ConstructionSimulatorTraining savedTraining = trainingRepository.save(training);
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

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateTraining(@PathVariable Long workerId, @PathVariable Long id, @RequestBody Map<String, Object> trainingData) {
        try {
            Optional<ConstructionSimulatorTraining> training = trainingRepository.findById(id);
            if (!training.isPresent() || !training.get().getWorker().getId().equals(workerId)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Training not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            ConstructionSimulatorTraining existingTraining = training.get();
            if (trainingData.containsKey("status")) {
                existingTraining.setStatus((String) trainingData.get("status"));
            }
            if (trainingData.containsKey("feedback")) {
                existingTraining.setFeedback((String) trainingData.get("feedback"));
            }
            if (trainingData.containsKey("notes")) {
                existingTraining.setNotes((String) trainingData.get("notes"));
            }

            ConstructionSimulatorTraining updatedTraining = trainingRepository.save(existingTraining);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", updatedTraining);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteTraining(@PathVariable Long workerId, @PathVariable Long id) {
        try {
            Optional<ConstructionSimulatorTraining> training = trainingRepository.findById(id);
            if (!training.isPresent() || !training.get().getWorker().getId().equals(workerId)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Training not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            trainingRepository.deleteById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Training deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

