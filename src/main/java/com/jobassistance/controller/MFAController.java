package com.jobassistance.controller;

import com.jobassistance.entity.User;
import com.jobassistance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * 多要素認証（MFA）管理コントローラー
 */
@RestController
@RequestMapping("/api/auth/mfa")
public class MFAController {

    @Autowired
    private UserRepository userRepository;

    /**
     * MFAセットアップ（シークレット生成）
     */
    @PostMapping("/setup")
    public ResponseEntity<Map<String, Object>> setupMFA(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            if (username == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Username is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Optional<User> userOpt = userRepository.findByUsername(username);
            if (!userOpt.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            User user = userOpt.get();
            // MFAシークレット生成（簡易版 - 本番環境では適切な実装が必要）
            String mfaSecret = UUID.randomUUID().toString().replace("-", "").substring(0, 32);
            user.setMfaSecret(mfaSecret);

            // QRコード用のURL生成（簡易版）
            String qrCodeUrl = "otpauth://totp/JobAssistance:" + username + "?secret=" + mfaSecret + "&issuer=JobAssistance";

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", Map.of(
                "mfaSecret", mfaSecret,
                "qrCodeUrl", qrCodeUrl
            ));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * MFA有効化
     */
    @PostMapping("/enable")
    public ResponseEntity<Map<String, Object>> enableMFA(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String mfaCode = request.get("mfaCode");

            if (username == null || mfaCode == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Username and MFA code are required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Optional<User> userOpt = userRepository.findByUsername(username);
            if (!userOpt.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            User user = userOpt.get();
            // MFAコード検証（簡易版 - 本番環境では適切な実装が必要）
            // ここでは簡易的にシークレットが設定されている場合に有効化
            if (user.getMfaSecret() != null) {
                user.setMfaEnabled(true);
                userRepository.save(user);

                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "MFA enabled successfully");
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "MFA secret not set. Please setup MFA first.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * MFA無効化
     */
    @PostMapping("/disable")
    public ResponseEntity<Map<String, Object>> disableMFA(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            if (username == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Username is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Optional<User> userOpt = userRepository.findByUsername(username);
            if (!userOpt.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            User user = userOpt.get();
            user.setMfaEnabled(false);
            user.setMfaSecret(null);
            user.setBackupCodes(null);
            userRepository.save(user);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "MFA disabled successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * バックアップコード生成
     */
    @PostMapping("/backup-codes")
    public ResponseEntity<Map<String, Object>> generateBackupCodes(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            if (username == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Username is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Optional<User> userOpt = userRepository.findByUsername(username);
            if (!userOpt.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            User user = userOpt.get();
            if (!user.getMfaEnabled()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "MFA is not enabled");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // バックアップコード生成（簡易版 - 本番環境では適切な実装が必要）
            String[] backupCodes = new String[10];
            for (int i = 0; i < 10; i++) {
                backupCodes[i] = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
            }
            String backupCodesJson = String.join(",", backupCodes);
            user.setBackupCodes(backupCodesJson);
            userRepository.save(user);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", Map.of("backupCodes", backupCodes));
            response.put("message", "Backup codes generated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

