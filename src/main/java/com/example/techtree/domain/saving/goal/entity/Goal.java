package com.example.techtree.domain.saving.goal.entity;

import static com.example.techtree.domain.saving.goal.entity.GoalStatus.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.techtree.domain.member.entity.Member;
import com.example.techtree.domain.saving.goal.dto.GoalDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Goal {

	@ManyToOne
	@JoinColumn(name = "memberId")
	private Member member;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long savingGoalId;

	//저축 목표 이름
	@Column(columnDefinition = "TEXT")
	private String goalName;

	//저축 목표 금액
	@Column
	private Long goalPrice;
	//저축 현재 금액
	@Column
	private Long currentPrice;

	//저축 목표 유형
	@Column
	private String goalType;

	//시작 날짜
	@Column
	private LocalDate startDate;

	//끝나는 날짜
	@Column
	private LocalDate endDate;

	//수정한 날짜
	@Column
	private LocalDateTime updateDate;

	@Enumerated(EnumType.STRING)
	private GoalStatus status; // 저축 목표 상태

	public void updateStatus() {
		if (this.currentPrice >= this.goalPrice) {
			this.status = GoalStatus.COMPLETED;
		} else {
			this.status = GoalStatus.IN_PROGRESS;
		}
	}

	public void updateCurrentPrice(Long savingPrice) {
		this.currentPrice += savingPrice;
	}

	public static Goal modifyGoal(Goal existingGoal, GoalDto goalDto) {
		return Goal.builder()
			.member(existingGoal.getMember()) // 기존 Goal의 member 정보를 유지
			.savingGoalId(existingGoal.getSavingGoalId())
			.goalName(goalDto.getGoalName())
			.goalType(goalDto.getGoalType())
			.startDate(goalDto.getStartDate())
			.endDate(goalDto.getEndDate())
			.goalPrice(goalDto.getGoalPrice())
			.currentPrice(goalDto.getCurrentPrice())
			.updateDate(LocalDateTime.now())
			.status(goalDto.getCurrentPrice() >= goalDto.getGoalPrice() ? COMPLETED : IN_PROGRESS)
			.build();
	}

	public double getAchievementRate() {
		return (double)currentPrice / goalPrice * 100;
	}
}
