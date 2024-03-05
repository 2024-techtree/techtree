package com.example.techtree.domain.financial.target.service;

import com.example.techtree.domain.financial.target.dto.TargetDto;
import com.example.techtree.domain.financial.target.entity.Target;

import java.util.List;

public interface TargetService {
    Target test1(TargetDto testDto);

    List<String> getAllTargetNames();

}
