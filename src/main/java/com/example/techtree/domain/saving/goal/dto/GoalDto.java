package com.example.techtree.domain.saving.goal.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.techtree.domain.saving.goal.entity.GoalStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoalDto {

	@NotBlank
	private String goalName;
	@NotBlank
	private String goalType;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd") // 날짜 형식 지정
	private LocalDate startDate;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd") // 날짜 형식 지정
	private LocalDate endDate;
	@NotNull
	private Long goalPrice;
	private Long currentPrice;

	@Enumerated(EnumType.STRING)
	private GoalStatus status; // 저축 목표 상태
}
