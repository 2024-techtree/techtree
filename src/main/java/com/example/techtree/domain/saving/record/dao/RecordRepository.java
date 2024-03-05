package com.example.techtree.domain.saving.record.dao;

import com.example.techtree.domain.saving.record.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {
    @Query("SELECT r.goalName FROM Record r")
    List<String> findAllGoalNames();
}
