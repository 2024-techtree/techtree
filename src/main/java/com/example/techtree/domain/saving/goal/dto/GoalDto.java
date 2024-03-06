package com.example.techtree.domain.saving.goal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

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

}
