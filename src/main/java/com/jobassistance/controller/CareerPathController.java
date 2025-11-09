package com.jobassistance.controller;

import com.jobassistance.entity.CareerPath;
import com.jobassistance.entity.Worker;
import com.jobassistance.repository.CareerPathRepository;
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
 * キャリアパス管理コントローラー
 */
@RestController
@RequestMapping("/api/workers/{workerId}/career-paths")
public class CareerPathController {

    @Autowired
    private CareerPathRepository careerPathRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getCareerPaths(@PathVariable Long workerId) {
        try {
            Optional<Worker> worker = workerRepository.findById(workerId);
            if (!worker.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            List<CareerPath> careerPaths = careerPathRepository.findByWorkerId(workerId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", careerPaths);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createCareerPath(@PathVariable Long workerId, @RequestBody Map<String, Object> careerPathData) {
        try {
            Optional<Worker> worker = workerRepository.findById(workerId);
            if (!worker.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            CareerPath careerPath = new CareerPath();
            careerPath.setWorker(worker.get());
            careerPath.setTitle((String) careerPathData.get("title"));
            careerPath.setDescription((String) careerPathData.get("description"));
            if (careerPathData.get("startDate") != null) {
                careerPath.setStartDate(java.time.LocalDate.parse((String) careerPathData.get("startDate")));
            }
            if (careerPathData.get("targetDate") != null) {
                careerPath.setTargetDate(java.time.LocalDate.parse((String) careerPathData.get("targetDate")));
            }
            if (careerPathData.get("careerLevel") != null) {
                careerPath.setCareerLevel((String) careerPathData.get("careerLevel"));
            }
            if (careerPathData.get("targetPosition") != null) {
                careerPath.setTargetPosition((String) careerPathData.get("targetPosition"));
            }
            if (careerPathData.get("requiredSkills") != null) {
                careerPath.setRequiredSkills((String) careerPathData.get("requiredSkills"));
            }
            if (careerPathData.get("actionPlan") != null) {
                careerPath.setActionPlan((String) careerPathData.get("actionPlan"));
            }
            if (careerPathData.get("status") != null) {
                careerPath.setStatus((String) careerPathData.get("status"));
            }
            careerPath.setNotes((String) careerPathData.get("notes"));

            CareerPath savedCareerPath = careerPathRepository.save(careerPath);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", savedCareerPath);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateCareerPath(@PathVariable Long workerId, @PathVariable Long id, @RequestBody Map<String, Object> careerPathData) {
        try {
            Optional<CareerPath> careerPath = careerPathRepository.findById(id);
            if (!careerPath.isPresent() || !careerPath.get().getWorker().getId().equals(workerId)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Career path not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            CareerPath existingCareerPath = careerPath.get();
            if (careerPathData.containsKey("title")) {
                existingCareerPath.setTitle((String) careerPathData.get("title"));
            }
            if (careerPathData.containsKey("description")) {
                existingCareerPath.setDescription((String) careerPathData.get("description"));
            }
            if (careerPathData.containsKey("startDate")) {
                existingCareerPath.setStartDate(java.time.LocalDate.parse((String) careerPathData.get("startDate")));
            }
            if (careerPathData.containsKey("targetDate")) {
                existingCareerPath.setTargetDate(java.time.LocalDate.parse((String) careerPathData.get("targetDate")));
            }
            if (careerPathData.containsKey("status")) {
                existingCareerPath.setStatus((String) careerPathData.get("status"));
            }
            if (careerPathData.containsKey("notes")) {
                existingCareerPath.setNotes((String) careerPathData.get("notes"));
            }

            CareerPath updatedCareerPath = careerPathRepository.save(existingCareerPath);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", updatedCareerPath);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteCareerPath(@PathVariable Long workerId, @PathVariable Long id) {
        try {
            Optional<CareerPath> careerPath = careerPathRepository.findById(id);
            if (!careerPath.isPresent() || !careerPath.get().getWorker().getId().equals(workerId)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Career path not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            careerPathRepository.deleteById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Career path deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

