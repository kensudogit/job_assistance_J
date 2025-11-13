package com.jobassistance.controller;

import com.jobassistance.entity.Report;
import com.jobassistance.repository.ReportRepository;
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
 * レポート管理コントローラー
 */
@RestController
@RequestMapping("/api")
public class ReportController {

    /** レポートリポジトリ */
    @Autowired
    private ReportRepository reportRepository;

    /** 就労者リポジトリ */
    @Autowired
    private WorkerRepository workerRepository;

    /**
     * 全体レポート一覧を取得する
     *
     * @return レポート一覧を含むレスポンス
     */
    @GetMapping("/reports")
    public ResponseEntity<Map<String, Object>> getAllReports() {
        try {
            List<Report> reports = reportRepository.findByWorkerIdIsNull();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", reports);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 全体レポートを登録する
     *
     * @param report レポート情報
     * @return 作成されたレポートを含むレスポンス
     */
    @PostMapping("/reports")
    public ResponseEntity<Map<String, Object>> createReport(@RequestBody Report report) {
        try {
            report.setWorker(null);
            Report savedReport = reportRepository.save(report);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", savedReport);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 就労者向けレポート一覧を取得する
     *
     * @param workerId 就労者ID
     * @return レポート一覧を含むレスポンス
     */
    @GetMapping("/workers/{workerId}/reports")
    public ResponseEntity<Map<String, Object>> getWorkerReports(@PathVariable Long workerId) {
        try {
            if (!workerRepository.existsById(workerId)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            List<Report> reports = reportRepository.findByWorkerId(workerId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", reports);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 就労者向けレポートを登録する
     *
     * @param workerId 就労者ID
     * @param report レポート情報
     * @return 作成されたレポートを含むレスポンス
     */
    @PostMapping("/workers/{workerId}/reports")
    public ResponseEntity<Map<String, Object>> createWorkerReport(@PathVariable Long workerId, @RequestBody Report report) {
        try {
            Optional<com.jobassistance.entity.Worker> worker = workerRepository.findById(workerId);
            if (!worker.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Worker not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            report.setWorker(worker.get());
            Report savedReport = reportRepository.save(report);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", savedReport);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * レポート詳細を取得する
     *
     * @param id レポートID
     * @return レポート詳細を含むレスポンス
     */
    @GetMapping("/reports/{id}")
    public ResponseEntity<Map<String, Object>> getReport(@PathVariable Long id) {
        try {
            Optional<Report> report = reportRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (report.isPresent()) {
                response.put("success", true);
                response.put("data", report.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Report not found");
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
     * レポート情報を更新する
     *
     * @param id レポートID
     * @param report 更新するレポート情報
     * @return 更新されたレポートを含むレスポンス
     */
    @PutMapping("/reports/{id}")
    public ResponseEntity<Map<String, Object>> updateReport(@PathVariable Long id, @RequestBody Report report) {
        try {
            Optional<Report> existingReport = reportRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (existingReport.isPresent()) {
                report.setId(id);
                report.setWorker(existingReport.get().getWorker());
                Report updatedReport = reportRepository.save(report);
                response.put("success", true);
                response.put("data", updatedReport);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Report not found");
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
     * レポートを削除する
     *
     * @param id レポートID
     * @return 削除結果を含むレスポンス
     */
    @DeleteMapping("/reports/{id}")
    public ResponseEntity<Map<String, Object>> deleteReport(@PathVariable Long id) {
        try {
            Optional<Report> report = reportRepository.findById(id);
            Map<String, Object> response = new HashMap<>();
            if (report.isPresent()) {
                reportRepository.deleteById(id);
                response.put("success", true);
                response.put("message", "Report deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Report not found");
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

