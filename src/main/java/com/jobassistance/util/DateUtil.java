package com.jobassistance.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 日付ユーティリティクラス
 */
public class DateUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * 文字列をLocalDateに変換する
     * 
     * @param dateString 日付文字列（ISO形式: yyyy-MM-dd）
     * @return 変換されたLocalDate（nullの場合はnullを返す）
     * @throws IllegalArgumentException 無効な日付形式の場合
     */
    public static LocalDate parseDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(dateString, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateString, e);
        }
    }

    /**
     * 文字列をLocalDateTimeに変換する
     * 
     * @param dateTimeString 日時文字列（ISO形式: yyyy-MM-ddTHH:mm:ss）
     * @return 変換されたLocalDateTime（nullの場合はnullを返す）
     * @throws IllegalArgumentException 無効な日時形式の場合
     */
    public static LocalDateTime parseDateTime(String dateTimeString) {
        if (dateTimeString == null || dateTimeString.isEmpty()) {
            return null;
        }
        try {
            return LocalDateTime.parse(dateTimeString, DATETIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid datetime format: " + dateTimeString, e);
        }
    }

    /**
     * LocalDateを文字列に変換する
     * 
     * @param date 日付
     * @return フォーマットされた日付文字列（ISO形式: yyyy-MM-dd）（nullの場合はnullを返す）
     */
    public static String formatDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.format(DATE_FORMATTER);
    }

    /**
     * LocalDateTimeを文字列に変換する
     * 
     * @param dateTime 日時
     * @return フォーマットされた日時文字列（ISO形式: yyyy-MM-ddTHH:mm:ss）（nullの場合はnullを返す）
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(DATETIME_FORMATTER);
    }
}
