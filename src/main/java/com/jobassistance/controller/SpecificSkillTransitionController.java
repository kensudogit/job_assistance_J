package com.jobassistance.controller;

import com.jobassistance.entity.SpecificSkillTransition;
import com.jobassistance.entity.Worker;
import com.jobassistance.repository.SpecificSkillTransitionRepository;
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
 * 特定技能移行管理コントローラー
 */
@RestController
@RequestMapping("/api/workers/{workerId}/specific-skill-transition")
public class SpecificSkillTransitionController {

    @Autowired
    private SpecificSkillTransitionRepository transitionRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getTransitions(@PathVariable Long workerId) {
        try {
            Optional<Worker> worker = workerRepository.findById(workerId);
            if (!worker.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            List<SpecificSkillTransition> transitions = transitionRepository.findByWorkerId(workerId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", transitions);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createTransition(@PathVariable Long workerId, @RequestBody Map<String, Object> transitionData) {
        try {
            Optional<Worker> worker = workerRepository.findById(workerId);
            if (!worker.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            SpecificSkillTransition transition = new SpecificSkillTransition();
            transition.setWorker(worker.get());
            if (transitionData.get("transitionDate") != null) {
                transition.setTransitionDate(java.time.LocalDate.parse((String) transitionData.get("transitionDate")));
            }
            transition.setFromSkill((String) transitionData.get("fromSkill"));
            transition.setToSkill((String) transitionData.get("toSkill"));
            transition.setTransitionType((String) transitionData.get("transitionType"));
            transition.setReason((String) transitionData.get("reason"));
            transition.setRequiredTraining((String) transitionData.get("requiredTraining"));
            transition.setSupportProvided((String) transitionData.get("supportProvided"));
            if (transitionData.get("status") != null) {
                transition.setStatus((String) transitionData.get("status"));
            }
            transition.setNotes((String) transitionData.get("notes"));

            SpecificSkillTransition savedTransition = transitionRepository.save(transition);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", savedTransition);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateTransition(@PathVariable Long workerId, @PathVariable Long id, @RequestBody Map<String, Object> transitionData) {
        try {
            Optional<SpecificSkillTransition> transition = transitionRepository.findById(id);
            if (!transition.isPresent() || !transition.get().getWorker().getId().equals(workerId)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Transition not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            SpecificSkillTransition existingTransition = transition.get();
            if (transitionData.containsKey("status")) {
                existingTransition.setStatus((String) transitionData.get("status"));
            }
            if (transitionData.containsKey("notes")) {
                existingTransition.setNotes((String) transitionData.get("notes"));
            }

            SpecificSkillTransition updatedTransition = transitionRepository.save(existingTransition);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", updatedTransition);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteTransition(@PathVariable Long workerId, @PathVariable Long id) {
        try {
            Optional<SpecificSkillTransition> transition = transitionRepository.findById(id);
            if (!transition.isPresent() || !transition.get().getWorker().getId().equals(workerId)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Transition not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            transitionRepository.deleteById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Transition deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

