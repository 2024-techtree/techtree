package com.example.techtree.domain.financial.target.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
    private LocalDate startDate;
    @NotBlank
    private LocalDate endDate;
    @NotBlank
    private Long targetPrice;




}
