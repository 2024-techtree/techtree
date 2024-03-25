package com.example.techtree.domain.saving.goal.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.techtree.domain.member.entity.Member;
import com.example.techtree.domain.saving.goal.entity.Goal;
import com.example.techtree.domain.saving.goal.entity.GoalStatus;

public interface GoalRepository extends JpaRepository<Goal, Long> {

	@Query("SELECT t.goalName FROM Goal t")
	List<String> findAllGoalNames();

	Goal findByGoalName(String goalName);

	Page<Goal> findByMemberMemberId(Long memberId, Pageable pageable);

	@Query("SELECT t.goalName FROM Goal t WHERE t.member.memberId = :memberId")
	List<String> findAllGoalNamesByMemberId(Long memberId);

	Goal findByGoalNameAndMember_MemberId(String goalName, Long memberId);

	Page<Goal> findByMemberOrderByUpdateDateDesc(Member member, Pageable pageable);

	@Query("SELECT g FROM Goal g WHERE g.member.memberId = :memberId AND g.status = :status")
	List<Goal> findByMemberIdAndStatus(@Param("memberId") Long memberId, @Param("status") GoalStatus status);

}
