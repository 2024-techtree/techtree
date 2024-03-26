package com.example.techtree.domain.saving.record.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.techtree.domain.saving.goal.entity.Goal;
import com.example.techtree.domain.saving.record.entity.Record;

public interface RecordRepository extends JpaRepository<Record, Long> {
	void deleteByGoal(Goal goal);

	Page<Record> findByGoal_SavingGoalId(Long savingGoalId, Pageable pageable);

	Page<Record> findAllByMember_MemberId(Long memberId, Pageable pageable);
}
