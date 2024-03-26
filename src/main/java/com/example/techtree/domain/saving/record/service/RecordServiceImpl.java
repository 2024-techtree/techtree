package com.example.techtree.domain.saving.record.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.techtree.domain.member.dao.MemberRepository;
import com.example.techtree.domain.member.entity.Member;
import com.example.techtree.domain.saving.goal.entity.Goal;
import com.example.techtree.domain.saving.goal.service.GoalService;
import com.example.techtree.domain.saving.record.dao.RecordRepository;
import com.example.techtree.domain.saving.record.dto.RecordDto;
import com.example.techtree.domain.saving.record.entity.Record;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RecordServiceImpl implements RecordService {
	private final RecordRepository recordRepository;
	private final GoalService goalService;
	private final MemberRepository memberRepository;

	@Override
	public Record savingRecordCreate(RecordDto recordDto, Long memberId) {
		Goal goal = goalService.findByGoalName(recordDto.getGoalName());
		if (goal == null) {
			throw new EntityNotFoundException("Goal not found with name: " + recordDto.getGoalName());
		}

		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));
		Record record = Record.builder()
			.goal(goal)
			.savingPrice(recordDto.getSavingPrice())
			.savingDate(recordDto.getSavingDate())
			.member(member)
			.build();

		recordRepository.save(record);

		goalService.updateCurrentPrice(goal.getSavingGoalId(), recordDto.getSavingPrice());
		goal.updateCurrentPrice(recordDto.getSavingPrice());
		return record;
	}

	@Override
	public Page<Record> getRecordsByGoalId(Long goalId, Pageable pageable) {
		return recordRepository.findByGoal_SavingGoalId(goalId, pageable);
	}

	@Override
	public Page<Record> getRecordsByMemberId(Long memberId, Pageable pageable) {
		// 여기에 memberId를 사용하여 모든 저축 기록을 조회하는 로직을 구현합니다.
		return recordRepository.findAllByMember_MemberId(memberId, pageable);
	}

}
