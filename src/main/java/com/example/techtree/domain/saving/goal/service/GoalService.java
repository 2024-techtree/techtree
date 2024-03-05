package com.example.techtree.domain.saving.goal.service;

import com.example.techtree.domain.saving.goal.dto.GoalDto;
import com.example.techtree.domain.saving.goal.entity.Goal;

import java.util.List;

public interface GoalService
{
    Goal savingGoalCreate(GoalDto testDto);

    Goal findGoalById(Long id);

    List<String> getAllGoalNames();

    Goal findByGoalName(String goalName);


}
