package com.example.techtree.domain.saving.goal.dao;

import com.example.techtree.domain.saving.goal.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {

    @Query("SELECT t.goalName FROM Goal t")
    List<String> findAllGoalNames();

    Goal findByGoalName(String goalName);
//    @Query("SELECT t.goalType FROM Goal t WHERE t.goalName = :goalName")
//    String findGoalTypeByGoalName(@Param("goalName") String goalName);
}
