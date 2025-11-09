package com.jobassistance.controller;

import com.jobassistance.entity.CareerGoal;
import com.jobassistance.entity.Worker;
import com.jobassistance.repository.CareerGoalRepository;
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
 * キャリア目標管理コントローラー
 */
@RestController
@RequestMapping("/api/workers/{workerId}/career-goals")
public class CareerGoalController {

    @Autowired
    private CareerGoalRepository goalRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getCareerGoals(@PathVariable Long workerId) {
        try {
            Optional<Worker> worker = workerRepository.findById(workerId);
            if (!worker.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            List<CareerGoal> goals = goalRepository.findByWorkerId(workerId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", goals);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createCareerGoal(@PathVariable Long workerId, @RequestBody Map<String, Object> goalData) {
        try {
            Optional<Worker> worker = workerRepository.findById(workerId);
            if (!worker.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            CareerGoal goal = new CareerGoal();
            goal.setWorker(worker.get());
            goal.setTitle((String) goalData.get("title"));
            goal.setDescription((String) goalData.get("description"));
            if (goalData.get("targetDate") != null) {
                goal.setTargetDate(java.time.LocalDate.parse((String) goalData.get("targetDate")));
            }
            if (goalData.get("goalCategory") != null) {
                goal.setGoalCategory((String) goalData.get("goalCategory"));
            }
            goal.setActionSteps((String) goalData.get("actionSteps"));
            goal.setSuccessCriteria((String) goalData.get("successCriteria"));
            if (goalData.get("progressPercentage") != null) {
                goal.setProgressPercentage(((Number) goalData.get("progressPercentage")).doubleValue());
            }
            if (goalData.get("status") != null) {
                goal.setStatus((String) goalData.get("status"));
            }
            goal.setNotes((String) goalData.get("notes"));

            CareerGoal savedGoal = goalRepository.save(goal);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", savedGoal);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateCareerGoal(@PathVariable Long workerId, @PathVariable Long id, @RequestBody Map<String, Object> goalData) {
        try {
            Optional<CareerGoal> goal = goalRepository.findById(id);
            if (!goal.isPresent() || !goal.get().getWorker().getId().equals(workerId)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Career goal not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            CareerGoal existingGoal = goal.get();
            if (goalData.containsKey("title")) {
                existingGoal.setTitle((String) goalData.get("title"));
            }
            if (goalData.containsKey("description")) {
                existingGoal.setDescription((String) goalData.get("description"));
            }
            if (goalData.containsKey("status")) {
                existingGoal.setStatus((String) goalData.get("status"));
            }
            if (goalData.containsKey("progressPercentage")) {
                existingGoal.setProgressPercentage(((Number) goalData.get("progressPercentage")).doubleValue());
            }
            if (goalData.containsKey("notes")) {
                existingGoal.setNotes((String) goalData.get("notes"));
            }

            CareerGoal updatedGoal = goalRepository.save(existingGoal);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", updatedGoal);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteCareerGoal(@PathVariable Long workerId, @PathVariable Long id) {
        try {
            Optional<CareerGoal> goal = goalRepository.findById(id);
            if (!goal.isPresent() || !goal.get().getWorker().getId().equals(workerId)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Career goal not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            goalRepository.deleteById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Career goal deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

