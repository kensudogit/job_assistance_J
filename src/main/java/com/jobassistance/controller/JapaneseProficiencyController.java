package com.jobassistance.controller;

import com.jobassistance.entity.JapaneseProficiency;
import com.jobassistance.repository.JapaneseProficiencyRepository;
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
 * 日本語能力管理コントローラー
 */
@RestController
@RequestMapping("/api/workers/{workerId}/japanese-proficiency")
public class JapaneseProficiencyController {

    @Autowired
    private JapaneseProficiencyRepository proficiencyRepository;

    @Autowired
    private WorkerRepository workerRepository;

    /**
     * 日本語能力一覧取得
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getProficiencyList(@PathVariable Long workerId) {
        try {
            if (!workerRepository.existsById(workerId)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            List<JapaneseProficiency> proficiencies = proficiencyRepository.findByWorkerId(workerId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", proficiencies);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 日本語能力登録
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createProficiency(@PathVariable Long workerId,
            @RequestBody JapaneseProficiency proficiency) {
        try {
            Optional<com.jobassistance.entity.Worker> worker = workerRepository.findById(workerId);
            if (!worker.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            proficiency.setWorker(worker.get());
            JapaneseProficiency savedProficiency = proficiencyRepository.save(proficiency);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", savedProficiency);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 日本語能力詳細取得
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProficiency(@PathVariable Long workerId, @PathVariable Long id) {
        try {
            Optional<JapaneseProficiency> proficiency = proficiencyRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (proficiency.isPresent() && proficiency.get().getWorker().getId().equals(workerId)) {
                response.put("success", true);
                response.put("data", proficiency.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Japanese proficiency not found");
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
     * 日本語能力更新
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateProficiency(@PathVariable Long workerId, @PathVariable Long id,
            @RequestBody JapaneseProficiency proficiency) {
        try {
            Optional<JapaneseProficiency> existingProficiency = proficiencyRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (existingProficiency.isPresent() && existingProficiency.get().getWorker().getId().equals(workerId)) {
                proficiency.setId(id);
                proficiency.setWorker(existingProficiency.get().getWorker());
                JapaneseProficiency updatedProficiency = proficiencyRepository.save(proficiency);
                response.put("success", true);
                response.put("data", updatedProficiency);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Japanese proficiency not found");
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
     * 日本語能力削除
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteProficiency(@PathVariable Long workerId, @PathVariable Long id) {
        try {
            Optional<JapaneseProficiency> proficiency = proficiencyRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (proficiency.isPresent() && proficiency.get().getWorker().getId().equals(workerId)) {
                proficiencyRepository.deleteById(id);
                response.put("success", true);
                response.put("message", "Japanese proficiency deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Japanese proficiency not found");
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
