package com.jobassistance.controller;

import com.jobassistance.entity.Message;
import com.jobassistance.repository.MessageRepository;
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
 * メッセージ管理コントローラー
 */
@RestController
@RequestMapping("/api/workers/{workerId}/messages")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private WorkerRepository workerRepository;

    /**
     * メッセージ一覧取得
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getMessageList(@PathVariable Long workerId) {
        try {
            if (!workerRepository.existsById(workerId)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            List<Message> messages = messageRepository.findByWorkerId(workerId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", messages);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * メッセージ登録
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createMessage(@PathVariable Long workerId,
            @RequestBody Message message) {
        try {
            Optional<com.jobassistance.entity.Worker> worker = workerRepository.findById(workerId);
            if (!worker.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            message.setWorker(worker.get());
            Message savedMessage = messageRepository.save(message);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", savedMessage);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * メッセージ詳細取得
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getMessage(@PathVariable Long workerId, @PathVariable Long id) {
        try {
            Optional<Message> message = messageRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (message.isPresent() && message.get().getWorker().getId().equals(workerId)) {
                response.put("success", true);
                response.put("data", message.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Message not found");
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
     * メッセージ更新
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateMessage(@PathVariable Long workerId, @PathVariable Long id,
            @RequestBody Message message) {
        try {
            Optional<Message> existingMessage = messageRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (existingMessage.isPresent() && existingMessage.get().getWorker().getId().equals(workerId)) {
                message.setId(id);
                message.setWorker(existingMessage.get().getWorker());
                Message updatedMessage = messageRepository.save(message);
                response.put("success", true);
                response.put("data", updatedMessage);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Message not found");
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
     * メッセージ削除
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteMessage(@PathVariable Long workerId, @PathVariable Long id) {
        try {
            Optional<Message> message = messageRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (message.isPresent() && message.get().getWorker().getId().equals(workerId)) {
                messageRepository.deleteById(id);
                response.put("success", true);
                response.put("message", "Message deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Message not found");
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
