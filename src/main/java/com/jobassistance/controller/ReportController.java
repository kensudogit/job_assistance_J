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

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private WorkerRepository workerRepository;

    /**
     * 全体レポート一覧取得
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
     * 全体レポート登録
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
     * 就労者向けレポート一覧取得
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
     * 就労者向けレポート登録
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
     * レポート詳細取得
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
     * レポート更新
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
     * レポート削除
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

