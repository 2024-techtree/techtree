package com.example.techtree.domain.saving.record.dao;

import com.example.techtree.domain.saving.record.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
