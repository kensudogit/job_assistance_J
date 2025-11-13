package com.jobassistance.controller;

import com.jobassistance.entity.Worker;
import com.jobassistance.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 統合ダッシュボードコントローラー
 */
@RestController
@RequestMapping("/api/workers/{workerId}/dashboard/integrated")
public class IntegratedDashboardController {

    /** 就労者リポジトリ */
    @Autowired
    private WorkerRepository workerRepository;

    /** 就労者進捗リポジトリ */
    @Autowired
    private WorkerProgressRepository progressRepository;

    /** 日本語能力リポジトリ */
    @Autowired
    private JapaneseProficiencyRepository japaneseProficiencyRepository;

    /** 技能訓練リポジトリ */
    @Autowired
    private SkillTrainingRepository skillTrainingRepository;

    /** 建設シミュレーター訓練リポジトリ */
    @Autowired
    private ConstructionSimulatorTrainingRepository simulatorTrainingRepository;

    /** 統合成長リポジトリ */
    @Autowired
    private IntegratedGrowthRepository integratedGrowthRepository;

    /** マイルストーンリポジトリ */
    @Autowired
    private MilestoneRepository milestoneRepository;

    /** キャリア目標リポジトリ */
    @Autowired
    private CareerGoalRepository careerGoalRepository;

    /**
     * 就労者の統合ダッシュボード情報を取得する
     *
     * @param workerId 就労者ID
     * @return 統合ダッシュボードデータを含むレスポンス
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getIntegratedDashboard(@PathVariable Long workerId) {
        try {
            Optional<Worker> worker = workerRepository.findById(workerId);
            if (!worker.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            // 統合ダッシュボードデータを構築
            Map<String, Object> dashboard = new HashMap<>();
            
            // 基本情報
            dashboard.put("workerId", workerId);
            dashboard.put("workerName", worker.get().getName());
            
            // 日本語能力
            long japaneseProficiencyCount = japaneseProficiencyRepository.findByWorkerId(workerId).size();
            dashboard.put("japaneseProficiencyCount", japaneseProficiencyCount);
            
            // 技能訓練
            long skillTrainingCount = skillTrainingRepository.findByWorkerId(workerId).size();
            dashboard.put("skillTrainingCount", skillTrainingCount);
            
            // シミュレーター訓練
            long simulatorTrainingCount = simulatorTrainingRepository.findByWorkerId(workerId).size();
            dashboard.put("simulatorTrainingCount", simulatorTrainingCount);
            
            // 統合成長記録
            long integratedGrowthCount = integratedGrowthRepository.findByWorkerId(workerId).size();
            dashboard.put("integratedGrowthCount", integratedGrowthCount);
            
            // マイルストーン
            long milestoneCount = milestoneRepository.findByWorkerId(workerId).size();
            dashboard.put("milestoneCount", milestoneCount);
            
            // キャリア目標
            long careerGoalCount = careerGoalRepository.findByWorkerId(workerId).size();
            dashboard.put("careerGoalCount", careerGoalCount);
            
            // 進捗記録
            long progressCount = progressRepository.findByWorkerId(workerId).size();
            dashboard.put("progressCount", progressCount);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", dashboard);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

