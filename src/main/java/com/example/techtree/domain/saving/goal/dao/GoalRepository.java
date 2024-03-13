package com.example.techtree.domain.saving.goal.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.techtree.domain.saving.goal.entity.Goal;

public interface GoalRepository extends JpaRepository<Goal, Long> {

	@Query("SELECT t.goalName FROM Goal t")
	List<String> findAllGoalNames();

	Goal findByGoalName(String goalName);

	Page<Goal> findByMemberMemberId(Long memberId, Pageable pageable);
}
