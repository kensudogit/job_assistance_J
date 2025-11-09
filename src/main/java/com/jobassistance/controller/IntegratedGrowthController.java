package com.jobassistance.controller;

import com.jobassistance.entity.IntegratedGrowth;
import com.jobassistance.entity.Worker;
import com.jobassistance.repository.IntegratedGrowthRepository;
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
 * 統合成長管理コントローラー
 */
@RestController
@RequestMapping("/api/workers/{workerId}/integrated-growth")
public class IntegratedGrowthController {

    @Autowired
    private IntegratedGrowthRepository growthRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getGrowths(@PathVariable Long workerId) {
        try {
            Optional<Worker> worker = workerRepository.findById(workerId);
            if (!worker.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            List<IntegratedGrowth> growths = growthRepository.findByWorkerId(workerId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", growths);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createGrowth(@PathVariable Long workerId, @RequestBody Map<String, Object> growthData) {
        try {
            Optional<Worker> worker = workerRepository.findById(workerId);
            if (!worker.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            IntegratedGrowth growth = new IntegratedGrowth();
            growth.setWorker(worker.get());
            if (growthData.get("recordDate") != null) {
                growth.setRecordDate(java.time.LocalDate.parse((String) growthData.get("recordDate")));
            }
            if (growthData.get("japaneseProficiencyScore") != null) {
                growth.setJapaneseProficiencyScore(((Number) growthData.get("japaneseProficiencyScore")).doubleValue());
            }
            if (growthData.get("technicalSkillScore") != null) {
                growth.setTechnicalSkillScore(((Number) growthData.get("technicalSkillScore")).doubleValue());
            }
            if (growthData.get("safetyAwarenessScore") != null) {
                growth.setSafetyAwarenessScore(((Number) growthData.get("safetyAwarenessScore")).doubleValue());
            }
            if (growthData.get("communicationScore") != null) {
                growth.setCommunicationScore(((Number) growthData.get("communicationScore")).doubleValue());
            }
            if (growthData.get("overallScore") != null) {
                growth.setOverallScore(((Number) growthData.get("overallScore")).doubleValue());
            }
            growth.setGrowthAreas((String) growthData.get("growthAreas"));
            growth.setImprovementPlan((String) growthData.get("improvementPlan"));
            growth.setAchievements((String) growthData.get("achievements"));
            growth.setChallenges((String) growthData.get("challenges"));
            growth.setNotes((String) growthData.get("notes"));

            IntegratedGrowth savedGrowth = growthRepository.save(growth);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", savedGrowth);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateGrowth(@PathVariable Long workerId, @PathVariable Long id, @RequestBody Map<String, Object> growthData) {
        try {
            Optional<IntegratedGrowth> growth = growthRepository.findById(id);
            if (!growth.isPresent() || !growth.get().getWorker().getId().equals(workerId)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Growth record not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            IntegratedGrowth existingGrowth = growth.get();
            if (growthData.containsKey("notes")) {
                existingGrowth.setNotes((String) growthData.get("notes"));
            }

            IntegratedGrowth updatedGrowth = growthRepository.save(existingGrowth);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", updatedGrowth);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteGrowth(@PathVariable Long workerId, @PathVariable Long id) {
        try {
            Optional<IntegratedGrowth> growth = growthRepository.findById(id);
            if (!growth.isPresent() || !growth.get().getWorker().getId().equals(workerId)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Growth record not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            growthRepository.deleteById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Growth record deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

