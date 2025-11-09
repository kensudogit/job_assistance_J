package com.jobassistance.controller;

import com.jobassistance.entity.TrainingMenu;
import com.jobassistance.repository.TrainingMenuRepository;
import com.jobassistance.service.TrainingMenuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 訓練メニュー管理コントローラー
 */
@RestController
@RequestMapping("/api/training-menus")
public class TrainingMenuController {

    @Autowired
    private TrainingMenuRepository trainingMenuRepository;

    @Autowired
    private TrainingMenuService trainingMenuService;

    /**
     * 訓練メニュー一覧取得
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllTrainingMenus() {
        try {
            List<TrainingMenu> menus = trainingMenuRepository.findAll();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", menus);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 訓練メニュー登録
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createTrainingMenu(@Valid @RequestBody TrainingMenu menu) {
        try {
            TrainingMenu savedMenu = trainingMenuRepository.save(menu);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", savedMenu);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 訓練メニュー詳細取得
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getTrainingMenu(@PathVariable Long id) {
        try {
            Optional<TrainingMenu> menu = trainingMenuRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (menu.isPresent()) {
                response.put("success", true);
                response.put("data", menu.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Training menu not found");
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
     * 訓練メニュー更新
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateTrainingMenu(@PathVariable Long id, @Valid @RequestBody TrainingMenu menu) {
        try {
            Optional<TrainingMenu> existingMenu = trainingMenuRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (existingMenu.isPresent()) {
                menu.setId(id);
                TrainingMenu updatedMenu = trainingMenuRepository.save(menu);
                response.put("success", true);
                response.put("data", updatedMenu);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Training menu not found");
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
     * 訓練メニュー削除
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteTrainingMenu(@PathVariable Long id) {
        try {
            Optional<TrainingMenu> menu = trainingMenuRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (menu.isPresent()) {
                trainingMenuRepository.deleteById(id);
                response.put("success", true);
                response.put("message", "Training menu deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Training menu not found");
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

