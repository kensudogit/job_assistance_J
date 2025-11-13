package com.jobassistance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 訓練メニューDTO
 */
@Data
public class TrainingMenuDTO {

    private Long id;

    @NotBlank(message = "メニュー名は必須です")
    @Size(max = 200, message = "メニュー名は200文字以内で入力してください")
    private String menuName;

    @NotBlank(message = "シナリオIDは必須です")
    @Size(max = 100, message = "シナリオIDは100文字以内で入力してください")
    private String scenarioId;

    private String scenarioDescription;

    private Double targetSafetyScore;

    private Integer targetErrorCount;

    private Double targetProcedureCompliance;

    private Integer targetWorkTime;

    private Double targetAchievementRate;

    @NotBlank(message = "使用重機は必須です")
    @Size(max = 100, message = "使用重機は100文字以内で入力してください")
    private String equipmentType;

    @NotBlank(message = "難易度は必須です")
    @Size(max = 20, message = "難易度は20文字以内で入力してください")
    private String difficultyLevel;

    private Integer timeLimit;

    private Boolean isActive = true;

    @Size(max = 100, message = "作成者は100文字以内で入力してください")
    private String createdBy;
}
