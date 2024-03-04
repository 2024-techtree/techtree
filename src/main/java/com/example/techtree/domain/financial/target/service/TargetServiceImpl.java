package com.example.techtree.domain.financial.target.service;

import com.example.techtree.domain.financial.target.dao.TargetRepository;
import com.example.techtree.domain.financial.target.dto.TargetDto;
import com.example.techtree.domain.financial.target.entity.Target;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TargetServiceImpl implements TargetService{


    private final TargetRepository targetRepository;
    @Override

    public Target test1(TargetDto testDto) {

        Target target = Target.builder()
                .targetType(testDto.getTargetType())
                .targetName(testDto.getTargetName())
                .targetPrice(testDto.getTargetPrice())
                .startDate(testDto.getStartDate())
                .endDate(testDto.getEndDate())
                .updateDate(LocalDateTime.now())
                .currentPrice(testDto.getCurrentPrice())
                .build();
        targetRepository.save(target);

        return target;
    }
}
