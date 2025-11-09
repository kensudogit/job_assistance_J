package com.jobassistance.controller;

import com.jobassistance.entity.Worker;
import com.jobassistance.entity.DigitalEvidence;
import com.jobassistance.repository.WorkerRepository;
import com.jobassistance.repository.DigitalEvidenceRepository;
import com.jobassistance.repository.JapaneseProficiencyRepository;
import com.jobassistance.repository.SkillTrainingRepository;
import com.jobassistance.repository.ConstructionSimulatorTrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 証拠レポートコントローラー
 */
@RestController
@RequestMapping("/api/workers/{workerId}/evidence-report")
public class EvidenceReportController {

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private DigitalEvidenceRepository evidenceRepository;

    @Autowired
    private JapaneseProficiencyRepository japaneseProficiencyRepository;

    @Autowired
    private SkillTrainingRepository skillTrainingRepository;

    @Autowired
    private ConstructionSimulatorTrainingRepository simulatorTrainingRepository;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getEvidenceReport(@PathVariable Long workerId) {
        try {
            Optional<Worker> worker = workerRepository.findById(workerId);
            if (!worker.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            // 証拠レポートデータを構築
            Map<String, Object> report = new HashMap<>();
            
            // 基本情報
            report.put("workerId", workerId);
            report.put("workerName", worker.get().getName());
            
            // デジタル証拠
            List<DigitalEvidence> evidences = evidenceRepository.findByWorkerId(workerId);
            report.put("digitalEvidences", evidences);
            
            // 日本語能力証明
            long japaneseProficiencyCount = japaneseProficiencyRepository.findByWorkerId(workerId).size();
            report.put("japaneseProficiencyCount", japaneseProficiencyCount);
            
            // 技能訓練証明
            long skillTrainingCount = skillTrainingRepository.findByWorkerId(workerId).size();
            report.put("skillTrainingCount", skillTrainingCount);
            
            // シミュレーター訓練証明
            long simulatorTrainingCount = simulatorTrainingRepository.findByWorkerId(workerId).size();
            report.put("simulatorTrainingCount", simulatorTrainingCount);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", report);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

