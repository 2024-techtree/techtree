package com.example.techtree.domain.saving.record.service;

import com.example.techtree.domain.saving.record.dto.RecordDto;
import com.example.techtree.domain.saving.record.entity.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecordService {
	Record savingRecordCreate(RecordDto recordDto);

    Page<Record> getAllRecords(Pageable pageable);
}
