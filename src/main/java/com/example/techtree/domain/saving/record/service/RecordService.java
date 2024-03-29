package com.example.techtree.domain.saving.record.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.techtree.domain.saving.record.dto.RecordDto;
import com.example.techtree.domain.saving.record.entity.Record;

public interface RecordService {

	Record savingRecordCreate(RecordDto recordDto, Long memberId);

	Page<Record> getRecordsByGoalId(Long goalId, Pageable pageable);

	Page<Record> getRecordsByMemberId(Long memberId, Pageable pageable);

}
