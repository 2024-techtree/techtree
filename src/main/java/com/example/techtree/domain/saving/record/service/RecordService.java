package com.example.techtree.domain.saving.record.service;

import com.example.techtree.domain.saving.record.dto.RecordDto;
import com.example.techtree.domain.saving.record.entity.Record;

public interface RecordService {
    Record savingRecordCreate(RecordDto recordDto);
}
