package com.example.techtree.domain.saving.record.service;

import com.example.techtree.domain.saving.goal.entity.Goal;
import com.example.techtree.domain.saving.goal.service.GoalService;
import com.example.techtree.domain.saving.record.dao.RecordRepository;
import com.example.techtree.domain.saving.record.dto.RecordDto;
import com.example.techtree.domain.saving.record.entity.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecordServiceImpl implements RecordService {
    private final RecordRepository recordRepository;
    private final GoalService goalService;

    @Override
    public Record test2(RecordDto recordDto) {
        Record record = Record.builder()
                .goalName(recordDto.getGoalName())
                .goalType(recordDto.getGoalType())
                .savingPrice(recordDto.getSavingPrice())
                .savingDate(recordDto.getSavingDate())
                .build();


        recordRepository.save(record);

        // Target 엔터티에서 CurrentPrice 업데이트
        Goal goal = goalService.findByGoalName(recordDto.getGoalName());
        goal.updateCurrentPrice(recordDto.getSavingPrice());
        return record;
    }
}
