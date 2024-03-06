package com.example.techtree.domain.saving.goal.service;

import com.example.techtree.domain.saving.goal.dto.GoalDto;
import com.example.techtree.domain.saving.goal.entity.Goal;

import java.util.List;

public interface GoalService {
	Goal savingGoalCreate(GoalDto testDto);

	Goal findGoalById(Long id);

	List<String> getAllGoalNames();

	void deleteGoalById(Long saving_goal_id);

	Goal findByGoalName(String goalName);

	List<Goal> getAllPosts();

	String getGoalType(String goalName);

	Goal modifyGoal(Long saving_goal_id, GoalDto goalDto);
}
