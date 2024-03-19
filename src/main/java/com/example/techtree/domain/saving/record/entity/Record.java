package com.example.techtree.domain.saving.record.entity;

import com.example.techtree.domain.member.entity.Member;
import com.example.techtree.domain.saving.goal.entity.Goal;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Record {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long savingWriteId;

	@ManyToOne
	@JoinColumn(name = "memberId")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "savingGoalId")
	private Goal goal;

	// 저축할 금액
	@Column
	private Long savingPrice;

	//저축 날짜
	@Column
	private LocalDate savingDate;
}
