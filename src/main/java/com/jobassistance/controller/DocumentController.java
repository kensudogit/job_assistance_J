package com.jobassistance.controller;

import com.jobassistance.entity.Document;
import com.jobassistance.repository.DocumentRepository;
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
 * ドキュメント管理コントローラー
 */
@RestController
@RequestMapping("/api/workers/{workerId}/documents")
public class DocumentController {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private WorkerRepository workerRepository;

    /**
     * ドキュメント一覧取得
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getDocumentList(@PathVariable Long workerId) {
        try {
            if (!workerRepository.existsById(workerId)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            List<Document> documents = documentRepository.findByWorkerId(workerId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", documents);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * ドキュメント登録
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createDocument(@PathVariable Long workerId, @RequestBody Document document) {
        try {
            Optional<com.jobassistance.entity.Worker> worker = workerRepository.findById(workerId);
            if (!worker.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            document.setWorker(worker.get());
            Document savedDocument = documentRepository.save(document);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", savedDocument);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * ドキュメント詳細取得
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getDocument(@PathVariable Long workerId, @PathVariable Long id) {
        try {
            Optional<Document> document = documentRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (document.isPresent() && document.get().getWorker().getId().equals(workerId)) {
                response.put("success", true);
                response.put("data", document.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Document not found");
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
     * ドキュメント更新
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateDocument(@PathVariable Long workerId, @PathVariable Long id, @RequestBody Document document) {
        try {
            Optional<Document> existingDocument = documentRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (existingDocument.isPresent() && existingDocument.get().getWorker().getId().equals(workerId)) {
                document.setId(id);
                document.setWorker(existingDocument.get().getWorker());
                Document updatedDocument = documentRepository.save(document);
                response.put("success", true);
                response.put("data", updatedDocument);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Document not found");
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
     * ドキュメント削除
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteDocument(@PathVariable Long workerId, @PathVariable Long id) {
        try {
            Optional<Document> document = documentRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (document.isPresent() && document.get().getWorker().getId().equals(workerId)) {
                documentRepository.deleteById(id);
                response.put("success", true);
                response.put("message", "Document deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Document not found");
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

