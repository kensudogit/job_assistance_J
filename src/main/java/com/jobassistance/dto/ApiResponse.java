package com.jobassistance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * APIレスポンスDTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    /** 成功フラグ */
    private boolean success;
    /** レスポンスデータ */
    private T data;
    /** メッセージ */
    private String message;
    /** エラーメッセージ */
    private String error;

    /**
     * 成功レスポンスを作成する
     * 
     * @param <T> データの型
     * @param data レスポンスデータ
     * @return 成功レスポンス
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null, null);
    }

    /**
     * 成功レスポンスを作成する（メッセージ付き）
     * 
     * @param <T> データの型
     * @param data レスポンスデータ
     * @param message メッセージ
     * @return 成功レスポンス
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, data, message, null);
    }

    /**
     * エラーレスポンスを作成する
     * 
     * @param <T> データの型
     * @param error エラーメッセージ
     * @return エラーレスポンス
     */
    public static <T> ApiResponse<T> error(String error) {
        return new ApiResponse<>(false, null, null, error);
    }
}
