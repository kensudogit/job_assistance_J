package com.jobassistance.controller;

import com.jobassistance.entity.Training;
import com.jobassistance.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 研修管理コントローラー
 */
@RestController
@RequestMapping("/api/trainings")
public class TrainingController {

    /** 研修リポジトリ */
    @Autowired
    private TrainingRepository trainingRepository;

    /**
     * すべての研修一覧を取得する
     * 
     * @return 研修一覧を含むレスポンス
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllTrainings() {
        try {
            List<Training> trainings = trainingRepository.findAll();
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

    /**
     * 新しい研修を登録する
     * 
     * @param training 研修情報
     * @return 作成された研修を含むレスポンス
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createTraining(@RequestBody Training training) {
        try {
            Training savedTraining = trainingRepository.save(training);
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

    /**
     * 指定されたIDの研修詳細を取得する
     * 
     * @param id 研修ID
     * @return 研修詳細を含むレスポンス
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getTraining(@PathVariable Long id) {
        try {
            Optional<Training> training = trainingRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (training.isPresent()) {
                response.put("success", true);
                response.put("data", training.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Training not found");
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
     * 研修情報を更新する
     * 
     * @param id 研修ID
     * @param training 更新する研修情報
     * @return 更新された研修を含むレスポンス
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateTraining(@PathVariable Long id, @RequestBody Training training) {
        try {
            Optional<Training> existingTraining = trainingRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (existingTraining.isPresent()) {
                training.setId(id);
                Training updatedTraining = trainingRepository.save(training);
                response.put("success", true);
                response.put("data", updatedTraining);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Training not found");
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
     * 研修を削除する
     * 
     * @param id 研修ID
     * @return 削除結果を含むレスポンス
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteTraining(@PathVariable Long id) {
        try {
            Optional<Training> training = trainingRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (training.isPresent()) {
                trainingRepository.deleteById(id);
                response.put("success", true);
                response.put("message", "Training deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Training not found");
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
