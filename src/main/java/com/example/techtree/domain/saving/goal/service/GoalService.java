package com.example.techtree.domain.saving.goal.service;

import com.example.techtree.domain.saving.goal.dto.GoalDto;
import com.example.techtree.domain.saving.goal.entity.Goal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GoalService {
	Goal savingGoalCreate(GoalDto testDto, Long memberId);

	Goal findGoalById(Long id);

	List<String> getAllGoalNames(Long memberId);

	void deleteGoalById(Long savingGoalId);

	Goal findByGoalName(String goalName);

	List<Goal> getAllPosts();

	String getGoalType(String goalName);

	Page<Goal> findGoals(Pageable pageable);

	Goal modifyGoal(Long savingGoalId, GoalDto goalDto);

	Page<Goal> findGoalsByMemberId(Long memberId, Pageable pageable);

}
