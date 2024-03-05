package com.example.techtree.domain.saving.goal.dao;

import com.example.techtree.domain.saving.goal.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal, Long> {
}
