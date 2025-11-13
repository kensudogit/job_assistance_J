package com.jobassistance.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * ヘルスチェックコントローラー
 */
@RestController
@RequestMapping("/api")
public class HealthController {

    /**
     * ヘルスチェックを実行する
     * システムの稼働状態を確認する
     *
     * @return システム状態を含むレスポンス
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "ok");
        response.put("timestamp", LocalDateTime.now());
        response.put("service", "Job Assistance System");
        response.put("version", "1.0.0");
        return ResponseEntity.ok(response);
    }
}

