package com.example.techtree.domain.financial.record.dao;

import com.example.techtree.domain.financial.record.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {
    @Query("SELECT r.targetName FROM Record r")
    List<String> findAllTargetNames();
}
