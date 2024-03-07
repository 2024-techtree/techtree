package com.example.techtree.domain.saving.record.entity;

import java.time.LocalDate;

import com.example.techtree.domain.saving.goal.entity.Goal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class Record {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long savingWrite_id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "saving_goal_id")
	private Goal goal;

	// 저축할 금액
	@Column
	private Long savingPrice;

	//저축 날짜
	@Column
	private LocalDate savingDate;
}
