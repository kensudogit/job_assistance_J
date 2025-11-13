package com.jobassistance.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

/**
 * グローバル例外ハンドラー
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 一般的な例外をハンドリングする
     * 
     * @param e 例外
     * @return エラーレスポンス（HTTP 500）
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception e) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("error", e.getMessage());
        response.put("type", e.getClass().getSimpleName());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * IllegalArgumentExceptionをハンドリングする
     * 
     * @param e 例外
     * @return エラーレスポンス（HTTP 400）
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("error", e.getMessage());
        response.put("type", "IllegalArgumentException");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * MethodArgumentTypeMismatchExceptionをハンドリングする
     * 
     * @param e 例外
     * @return エラーレスポンス（HTTP 400）
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("error", "Invalid parameter type: " + e.getName());
        response.put("type", "MethodArgumentTypeMismatchException");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * NullPointerExceptionをハンドリングする
     * 
     * @param e 例外
     * @return エラーレスポンス（HTTP 400）
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String, Object>> handleNullPointerException(NullPointerException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("error", "Required parameter is missing");
        response.put("type", "NullPointerException");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
