package com.jobassistance.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * JSONユーティリティクラス
 */
public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    /**
     * オブジェクトをJSON文字列に変換する
     * 
     * @param obj 変換するオブジェクト
     * @return JSON文字列
     * @throws RuntimeException JSON変換に失敗した場合
     */
    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert object to JSON", e);
        }
    }

    /**
     * JSON文字列をオブジェクトに変換する
     * 
     * @param <T> 変換先の型
     * @param json JSON文字列
     * @param clazz 変換先のクラス
     * @return 変換されたオブジェクト
     * @throws RuntimeException JSON変換に失敗した場合
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON to object", e);
        }
    }

    /**
     * ObjectMapperを取得する
     * 
     * @return ObjectMapperインスタンス
     */
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}

