package com.example.techtree.domain.financial.target.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TargetDto {
    @NotBlank
    private String targetName;
    @NotBlank
    private String targetType;
    @NotBlank
    private LocalDateTime startDate;
    @NotBlank
    private LocalDateTime endDate;
    @NotBlank
    private Long targetPrice;
    private Long currentPrice;


}
