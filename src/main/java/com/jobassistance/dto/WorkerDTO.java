package com.jobassistance.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

/**
 * 就労者DTO
 */
@Data
public class WorkerDTO {

    private Long id;

    @NotBlank(message = "名前は必須です")
    @Size(max = 100, message = "名前は100文字以内で入力してください")
    private String name;

    @Size(max = 100, message = "カナ名は100文字以内で入力してください")
    private String nameKana;

    @NotBlank(message = "メールアドレスは必須です")
    @Email(message = "有効なメールアドレスを入力してください")
    @Size(max = 100, message = "メールアドレスは100文字以内で入力してください")
    private String email;

    @Size(max = 500, message = "電話番号は500文字以内で入力してください")
    private String phone;

    @Size(max = 200, message = "住所は200文字以内で入力してください")
    private String address;

    private LocalDate birthDate;

    @Size(max = 100, message = "国籍は100文字以内で入力してください")
    private String nationality;

    @Size(max = 50, message = "母国語は50文字以内で入力してください")
    private String nativeLanguage;

    @Size(max = 50, message = "在留資格は50文字以内で入力してください")
    private String visaStatus;

    private LocalDate visaExpiryDate;

    @Size(max = 20, message = "日本語レベルは20文字以内で入力してください")
    private String japaneseLevel;

    @Size(max = 20, message = "英語レベルは20文字以内で入力してください")
    private String englishLevel;

    private String skills;

    private Integer experienceYears = 0;

    @Size(max = 200, message = "学歴は200文字以内で入力してください")
    private String education;

    @Size(max = 50, message = "現在のステータスは50文字以内で入力してください")
    private String currentStatus = "登録中";

    private String notes;
}

