package com.example.techtree.domain.saving.goal.service;

import java.util.List;

import com.example.techtree.domain.saving.goal.dto.GoalDto;
import com.example.techtree.domain.saving.goal.entity.Goal;

public interface GoalService {
	Goal savingGoalCreate(GoalDto testDto);

	Goal findGoalById(Long id);

	List<String> getAllGoalNames();

	void deleteGoalById(Long saving_goal_id);

	Goal findByGoalName(String goalName);

	List<Goal> getAllPosts();
}
