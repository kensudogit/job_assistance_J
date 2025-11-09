package com.jobassistance.controller;

import com.jobassistance.entity.TrainingSession;
import com.jobassistance.repository.TrainingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * リプレイセッションコントローラー
 */
@RestController
@RequestMapping("/api/replay")
public class ReplayController {

    @Autowired
    private TrainingSessionRepository trainingSessionRepository;

    /**
     * リプレイセッションデータ取得
     */
    @GetMapping("/{sessionId}")
    public ResponseEntity<Map<String, Object>> getReplaySession(@PathVariable String sessionId) {
        try {
            Optional<TrainingSession> sessionOpt = trainingSessionRepository.findBySessionId(sessionId);
            if (!sessionOpt.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Session not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            TrainingSession session = sessionOpt.get();

            // リプレイデータを構築
            Map<String, Object> replayData = new HashMap<>();
            replayData.put("sessionId", session.getSessionId());
            replayData.put("workerId", session.getWorker().getId());
            replayData.put("sessionStartTime", session.getSessionStartTime());
            replayData.put("sessionEndTime", session.getSessionEndTime());
            replayData.put("durationSeconds", session.getDurationSeconds());
            // 実際のリプレイデータはここに追加（操作ログ、KPIスコアなど）

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", replayData);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

