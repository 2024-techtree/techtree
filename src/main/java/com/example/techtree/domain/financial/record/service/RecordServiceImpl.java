package com.example.techtree.domain.financial.record.service;

import com.example.techtree.domain.financial.record.dao.RecordRepository;
import com.example.techtree.domain.financial.record.dto.RecordDto;
import com.example.techtree.domain.financial.record.entity.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecordServiceImpl implements RecordService{
    private final RecordRepository recordRepository;
    @Override
    public Record test2(RecordDto recordDto) {
        Record record = Record.builder()
                .targetName(recordDto.getTargetName())
                .targetType(recordDto.getTargetType())
                .saving_Price(recordDto.getSaving_Price())
                .saving_Date(recordDto.getSaving_Date())
                .build();
        recordRepository.save(record);

        return record;
    }
}
