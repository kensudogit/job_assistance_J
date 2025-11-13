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

    /** ドキュメントリポジトリ */
    @Autowired
    private DocumentRepository documentRepository;

    /** 就労者リポジトリ */
    @Autowired
    private WorkerRepository workerRepository;

    /**
     * 就労者のドキュメント一覧を取得する
     * 
     * @param workerId 就労者ID
     * @return ドキュメント一覧を含むレスポンス
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
     * 新しいドキュメントを登録する
     * 
     * @param workerId 就労者ID
     * @param document ドキュメント情報
     * @return 作成されたドキュメントを含むレスポンス
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createDocument(@PathVariable Long workerId,
            @RequestBody Document document) {
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
     * 指定されたIDのドキュメント詳細を取得する
     * 
     * @param workerId 就労者ID
     * @param id ドキュメントID
     * @return ドキュメント詳細を含むレスポンス
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
     * ドキュメント情報を更新する
     * 
     * @param workerId 就労者ID
     * @param id ドキュメントID
     * @param document 更新するドキュメント情報
     * @return 更新されたドキュメントを含むレスポンス
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateDocument(@PathVariable Long workerId, @PathVariable Long id,
            @RequestBody Document document) {
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
     * ドキュメントを削除する
     * 
     * @param workerId 就労者ID
     * @param id ドキュメントID
     * @return 削除結果を含むレスポンス
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
