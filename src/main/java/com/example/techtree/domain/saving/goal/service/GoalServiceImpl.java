package com.example.techtree.domain.saving.goal.service;

import com.example.techtree.domain.saving.goal.dao.GoalRepository;
import com.example.techtree.domain.saving.goal.dto.GoalDto;
import com.example.techtree.domain.saving.goal.entity.Goal;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GoalServiceImpl implements GoalService {

	private final GoalRepository goalRepository;

	@Override
	public Goal savingGoalCreate(GoalDto goalDto) {
		Goal goal = Goal.builder()
			.goalType(goalDto.getGoalType())
			.goalName(goalDto.getGoalName())
			.goalPrice(goalDto.getGoalPrice())
			.startDate(goalDto.getStartDate())
			.endDate(goalDto.getEndDate())
			.updateDate(LocalDateTime.now()) // 현재 시간으로 업데이트 날짜 설정
			.currentPrice(goalDto.getCurrentPrice())
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
	public List<String> getAllGoalNames() {
		return goalRepository.findAllGoalNames();
	}

	@Override
	public void deleteGoalById(Long saving_goal_id) {
		goalRepository.deleteById(saving_goal_id);
	}

	@Override
	public Goal findByGoalName(String goalName) {
		return goalRepository.findByGoalName(goalName);
	}

	@Override
	public List<Goal> getAllPosts() {
		return goalRepository.findAll();
	}

	public String getGoalType(String goalName) {
		Goal goal = goalRepository.findByGoalName(goalName);
		return goal != null ? goal.getGoalType() : "";
	}

	@Override
	public Goal modifyGoal(Long saving_goal_id, GoalDto goalDto) {
		Goal existingGoal = goalRepository.findById(saving_goal_id)
				.orElseThrow(() -> new EntityNotFoundException("Goal not found with id: " + saving_goal_id));

		// Goal 엔티티 수정을 빌더 패턴으로 진행합니다.
		Goal modifiedGoal = Goal.modifyGoal(existingGoal, goalDto);

		return goalRepository.save(modifiedGoal);
	}
}
