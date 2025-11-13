package com.jobassistance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * エラーレスポンスDTO
 * APIエラー応答のデータ転送オブジェクト
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    /** 成功フラグ（常にfalse） */
    private boolean success = false;
    
    /** エラーメッセージ */
    private String error;
    
    /** エラータイプ */
    private String type;
    
    /** エラー発生時刻 */
    private LocalDateTime timestamp = LocalDateTime.now();
}

