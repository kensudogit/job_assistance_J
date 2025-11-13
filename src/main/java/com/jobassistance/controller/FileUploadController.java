package com.jobassistance.controller;

import com.jobassistance.entity.Document;
import com.jobassistance.entity.Worker;
import com.jobassistance.repository.DocumentRepository;
import com.jobassistance.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * ファイルアップロードコントローラー
 */
@RestController
@RequestMapping("/api/workers")
public class FileUploadController {

    /** 就労者リポジトリ */
    @Autowired
    private WorkerRepository workerRepository;

    /** ドキュメントリポジトリ */
    @Autowired
    private DocumentRepository documentRepository;

    /**
     * スクリーンショットをアップロードする
     *
     * @param workerId 就労者ID
     * @param file アップロードするファイル
     * @return アップロード結果を含むレスポンス
     */
    @PostMapping("/screenshot")
    public ResponseEntity<Map<String, Object>> uploadScreenshot(
            @RequestParam("workerId") Long workerId,
            @RequestParam("file") MultipartFile file) {
        try {
            Optional<Worker> worker = workerRepository.findById(workerId);
            if (!worker.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            if (file.isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "File is empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // ファイル情報を取得
            String fileName = file.getOriginalFilename();
            String contentType = file.getContentType();
            long fileSize = file.getSize();

            // ドキュメントエンティティを作成
            Document document = new Document();
            document.setWorker(worker.get());
            document.setDocumentType("screenshot");
            document.setTitle(fileName != null ? fileName : "Screenshot");
            document.setFileName(fileName);
            document.setMimeType(contentType);
            document.setFileSize(fileSize);
            // 実際のファイル保存処理はここに実装（ファイルシステムまたはストレージサービス）

            Document savedDocument = documentRepository.save(document);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", Map.of(
                    "documentId", savedDocument.getId(),
                    "fileName", fileName,
                    "fileSize", fileSize));
            response.put("message", "File uploaded successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * ドキュメントをアップロードする
     *
     * @param workerId 就労者ID
     * @param file アップロードするファイル
     * @param documentType ドキュメントタイプ
     * @param title ドキュメントタイトル
     * @return アップロード結果を含むレスポンス
     */
    @PostMapping("/{workerId}/documents/upload")
    public ResponseEntity<Map<String, Object>> uploadDocument(
            @PathVariable Long workerId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "documentType", required = false, defaultValue = "other") String documentType,
            @RequestParam(value = "title", required = false) String title) {
        try {
            Optional<Worker> worker = workerRepository.findById(workerId);
            if (!worker.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            if (file.isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "File is empty");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // ファイル情報を取得
            String fileName = file.getOriginalFilename();
            String contentType = file.getContentType();
            long fileSize = file.getSize();

            // ドキュメントエンティティを作成
            Document document = new Document();
            document.setWorker(worker.get());
            document.setDocumentType(documentType);
            document.setTitle(title != null ? title : (fileName != null ? fileName : "Document"));
            document.setFileName(fileName);
            document.setMimeType(contentType);
            document.setFileSize(fileSize);
            // 実際のファイル保存処理はここに実装（ファイルシステムまたはストレージサービス）

            Document savedDocument = documentRepository.save(document);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", Map.of(
                    "documentId", savedDocument.getId(),
                    "fileName", fileName,
                    "fileSize", fileSize));
            response.put("message", "Document uploaded successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
