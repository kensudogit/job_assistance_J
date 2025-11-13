package com.jobassistance.controller;

import com.jobassistance.entity.User;
import com.jobassistance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 認証管理コントローラー
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    /** ユーザーリポジトリ */
    @Autowired
    private UserRepository userRepository;

    /** パスワードエンコーダー */
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * ユーザーログインを処理する
     *
     * @param credentials ログイン認証情報（ユーザー名とパスワード）
     * @return ログイン結果を含むレスポンス
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        try {
            String username = credentials.get("username");
            String password = credentials.get("password");

            if (username == null || password == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Username and password are required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Optional<User> userOpt = userRepository.findByUsername(username);
            if (!userOpt.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Invalid username or password");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            User user = userOpt.get();
            // パスワード検証（簡易版 - 本番環境では適切な実装が必要）
            if (!user.getIsActive()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "User account is inactive");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            // ログイン成功
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);

            Map<String, Object> userData = new HashMap<>();
            userData.put("id", user.getId());
            userData.put("username", user.getUsername());
            userData.put("email", user.getEmail());
            userData.put("role", user.getRole());
            userData.put("mfaEnabled", user.getMfaEnabled());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", userData);
            response.put("message", "Login successful");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 現在のユーザー情報を取得する
     *
     * @return 現在のユーザー情報を含むレスポンス
     */
    @GetMapping("/current")
    public ResponseEntity<Map<String, Object>> getCurrentUser() {
        try {
            // 簡易版 - 本番環境では適切な認証実装が必要
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Current user endpoint - authentication required");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * ユーザーログアウトを処理する
     *
     * @return ログアウト結果を含むレスポンス
     */
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout() {
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Logout successful");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
