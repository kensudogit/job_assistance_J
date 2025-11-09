package com.jobassistance.controller;

import com.jobassistance.entity.Milestone;
import com.jobassistance.entity.Worker;
import com.jobassistance.repository.MilestoneRepository;
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
 * マイルストーン管理コントローラー
 */
@RestController
@RequestMapping("/api/workers/{workerId}/milestones")
public class MilestoneController {

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private WorkerRepository workerRepository;

    /**
     * マイルストーン一覧取得
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getMilestones(@PathVariable Long workerId) {
        try {
            Optional<Worker> worker = workerRepository.findById(workerId);
            if (!worker.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            List<Milestone> milestones = milestoneRepository.findByWorkerId(workerId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", milestones);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * マイルストーン登録
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createMilestone(@PathVariable Long workerId, @RequestBody Map<String, Object> milestoneData) {
        try {
            Optional<Worker> worker = workerRepository.findById(workerId);
            if (!worker.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            Milestone milestone = new Milestone();
            milestone.setWorker(worker.get());
            milestone.setTitle((String) milestoneData.get("title"));
            milestone.setDescription((String) milestoneData.get("description"));
            if (milestoneData.get("targetDate") != null) {
                milestone.setTargetDate(java.time.LocalDate.parse((String) milestoneData.get("targetDate")));
            }
            if (milestoneData.get("status") != null) {
                milestone.setStatus((String) milestoneData.get("status"));
            }
            if (milestoneData.get("category") != null) {
                milestone.setCategory((String) milestoneData.get("category"));
            }
            milestone.setNotes((String) milestoneData.get("notes"));

            Milestone savedMilestone = milestoneRepository.save(milestone);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", savedMilestone);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * マイルストーン更新
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateMilestone(@PathVariable Long workerId, @PathVariable Long id, @RequestBody Map<String, Object> milestoneData) {
        try {
            Optional<Milestone> milestone = milestoneRepository.findById(id);
            if (!milestone.isPresent() || !milestone.get().getWorker().getId().equals(workerId)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Milestone not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            Milestone existingMilestone = milestone.get();
            if (milestoneData.containsKey("title")) {
                existingMilestone.setTitle((String) milestoneData.get("title"));
            }
            if (milestoneData.containsKey("description")) {
                existingMilestone.setDescription((String) milestoneData.get("description"));
            }
            if (milestoneData.containsKey("targetDate")) {
                existingMilestone.setTargetDate(java.time.LocalDate.parse((String) milestoneData.get("targetDate")));
            }
            if (milestoneData.containsKey("status")) {
                existingMilestone.setStatus((String) milestoneData.get("status"));
            }
            if (milestoneData.containsKey("category")) {
                existingMilestone.setCategory((String) milestoneData.get("category"));
            }
            if (milestoneData.containsKey("notes")) {
                existingMilestone.setNotes((String) milestoneData.get("notes"));
            }

            Milestone updatedMilestone = milestoneRepository.save(existingMilestone);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", updatedMilestone);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * マイルストーン削除
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteMilestone(@PathVariable Long workerId, @PathVariable Long id) {
        try {
            Optional<Milestone> milestone = milestoneRepository.findById(id);
            if (!milestone.isPresent() || !milestone.get().getWorker().getId().equals(workerId)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Milestone not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            milestoneRepository.deleteById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Milestone deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

