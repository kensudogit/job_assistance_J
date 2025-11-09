package com.jobassistance.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * CSRFトークン管理コントローラー
 */
@RestController
@RequestMapping("/api/auth")
public class CSRFController {

    /**
     * CSRFトークン取得
     */
    @GetMapping("/csrf-token")
    public ResponseEntity<Map<String, Object>> getCSRFToken() {
        try {
            // CSRFトークン生成（簡易版 - 本番環境では適切な実装が必要）
            String csrfToken = UUID.randomUUID().toString();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", Map.of("csrfToken", csrfToken));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}

