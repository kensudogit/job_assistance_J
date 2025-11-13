package com.jobassistance.controller;

import com.jobassistance.entity.User;
import com.jobassistance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * ユーザー管理コントローラー
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    /** ユーザーリポジトリ */
    @Autowired
    private UserRepository userRepository;

    /** パスワードエンコーダー */
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * ユーザー一覧を取得する
     *
     * @return ユーザー一覧を含むレスポンス
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            // パスワードハッシュを除外
            users.forEach(user -> user.setPasswordHash(null));
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", users);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 新しいユーザーを登録する
     *
     * @param userData ユーザーデータ
     * @return 作成されたユーザーを含むレスポンス
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody Map<String, String> userData) {
        try {
            String username = userData.get("username");
            String password = userData.get("password");
            String email = userData.get("email");
            String role = userData.getOrDefault("role", "trainee");

            if (username == null || password == null || email == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Username, password, and email are required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (userRepository.existsByUsername(username)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Username already exists");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            if (userRepository.existsByEmail(email)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Email already exists");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            User user = new User();
            user.setUsername(username);
            user.setPasswordHash(passwordEncoder.encode(password));
            user.setEmail(email);
            user.setRole(role);
            user.setIsActive(true);

            User savedUser = userRepository.save(user);
            savedUser.setPasswordHash(null); // パスワードハッシュを除外

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", savedUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * ユーザー詳細を取得する
     *
     * @param id ユーザーID
     * @return ユーザー詳細を含むレスポンス
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUser(@PathVariable Long id) {
        try {
            Optional<User> user = userRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (user.isPresent()) {
                user.get().setPasswordHash(null); // パスワードハッシュを除外
                response.put("success", true);
                response.put("data", user.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "User not found");
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
     * ユーザー情報を更新する
     *
     * @param id ユーザーID
     * @param userData 更新するユーザーデータ
     * @return 更新されたユーザーを含むレスポンス
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody Map<String, String> userData) {
        try {
            Optional<User> existingUser = userRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (!existingUser.isPresent()) {
                response.put("success", false);
                response.put("error", "User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            User user = existingUser.get();
            if (userData.containsKey("email")) {
                user.setEmail(userData.get("email"));
            }
            if (userData.containsKey("role")) {
                user.setRole(userData.get("role"));
            }
            if (userData.containsKey("isActive")) {
                user.setIsActive(Boolean.parseBoolean(userData.get("isActive")));
            }
            if (userData.containsKey("password")) {
                user.setPasswordHash(passwordEncoder.encode(userData.get("password")));
            }

            User updatedUser = userRepository.save(user);
            updatedUser.setPasswordHash(null); // パスワードハッシュを除外

            response.put("success", true);
            response.put("data", updatedUser);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * ユーザーを削除する
     *
     * @param id ユーザーID
     * @return 削除結果を含むレスポンス
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
        try {
            Optional<User> user = userRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (user.isPresent()) {
                userRepository.deleteById(id);
                response.put("success", true);
                response.put("message", "User deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "User not found");
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

