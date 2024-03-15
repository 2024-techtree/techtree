package com.example.techtree.domain.saving.goal.service;

import com.example.techtree.domain.member.dao.MemberRepository;
import com.example.techtree.domain.member.entity.Member;
import com.example.techtree.domain.saving.goal.dao.GoalRepository;
import com.example.techtree.domain.saving.goal.dto.GoalDto;
import com.example.techtree.domain.saving.goal.entity.Goal;
import com.example.techtree.domain.saving.record.dao.RecordRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GoalServiceImpl implements GoalService {

	private final GoalRepository goalRepository;
	private final RecordRepository recordRepository;
	private final MemberRepository memberRepository;

	@Override
	public Goal savingGoalCreate(GoalDto goalDto, Long memberId) {

		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));
		Goal goal = Goal.builder()
			.goalType(goalDto.getGoalType())
			.goalName(goalDto.getGoalName())
			.goalPrice(goalDto.getGoalPrice())
			.startDate(goalDto.getStartDate())
			.endDate(goalDto.getEndDate())
			.updateDate(LocalDateTime.now()) // 현재 시간으로 업데이트 날짜 설정
			.currentPrice(goalDto.getCurrentPrice())
			.member(member)
			.build();
		goalRepository.save(goal);

		return goal;
	}

	@Override
	public Goal findGoalById(Long id) {
		// findById 메소드는 Optional<Goal>을 반환하므로, orElseThrow 등을 사용해 Goal 객체를 가져옵니다.
		return goalRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Goal not found with id: " + id));
	}

	@Override
	public List<String> getAllGoalNames(Long memberId) {
		return goalRepository.findAllGoalNamesByMemberId(memberId);
	}

	@Override
	@Transactional
	public void deleteGoalById(Long savingGoalId) {
		Goal goal = goalRepository.findById(savingGoalId)
			.orElseThrow(() -> new EntityNotFoundException("Goal not found with id: " + savingGoalId));

		// Goal을 참조하는 Record 삭제
		recordRepository.deleteByGoal(goal);

		// Goal 삭제
		goalRepository.delete(goal);
	}

	@Override
	public Goal findByGoalName(String goalName) {
		return goalRepository.findByGoalName(goalName);
	}

	@Override
	public List<Goal> getAllPosts() {
		return goalRepository.findAll();
	}

	@Override
	public String getGoalType(String goalName, Long memberId) {
		Goal goal = goalRepository.findByGoalNameAndMember_MemberId(goalName, memberId);
		return goal != null ? goal.getGoalType() : "";
	}

	@Override
	public Page<Goal> findGoals(Pageable pageable) {
		return goalRepository.findAll(pageable);
	}

	@Override
	public Goal modifyGoal(Long savingGoalId, GoalDto goalDto) {
		Goal existingGoal = goalRepository.findById(savingGoalId)
			.orElseThrow(() -> new EntityNotFoundException("Goal not found with id: " + savingGoalId));

		// Goal 엔티티 수정을 빌더 패턴으로 진행합니다.
		Goal modifiedGoal = Goal.modifyGoal(existingGoal, goalDto);

		return goalRepository.save(modifiedGoal);
	}

	@Override
	public Page<Goal> findGoalsByMemberId(Long memberId, Pageable pageable) {
		return goalRepository.findByMemberMemberId(memberId, pageable);
	}


}
