package com.example.techtree.domain.saving.goal.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.techtree.domain.saving.goal.dto.GoalDto;
import com.example.techtree.domain.saving.goal.entity.Goal;
import com.example.techtree.domain.saving.goal.entity.GoalStatus;

public interface GoalService {
	Goal savingGoalCreate(GoalDto testDto, Long memberId);

	Goal findGoalById(Long id);

	List<String> getAllGoalNames(Long memberId);

	void deleteGoalById(Long savingGoalId);

	Goal findByGoalName(String goalName);

	List<Goal> getAllPosts();

	Page<Goal> findGoals(Pageable pageable);

	Goal modifyGoal(Long savingGoalId, GoalDto goalDto);

	Page<Goal> findGoalsByMemberId(Long memberId, Pageable pageable);

	String getGoalType(String goalName, Long memberId);

	boolean isDuplicateGoalName(String goalName, Long memberId);

	List<Goal> findTop5GoalsByMemberId(Long memberId);

	void updateCurrentPrice(Long goalId, Long savingPrice);

	List<Goal> findByMemberIdAndStatus(Long memberId, GoalStatus status);

	List<Goal> findTop5CompletedGoalsByMemberId(Long memberId, GoalStatus status);
}
