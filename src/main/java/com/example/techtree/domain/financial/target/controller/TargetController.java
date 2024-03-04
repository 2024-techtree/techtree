package com.example.techtree.domain.financial.target.controller;

import com.example.techtree.domain.financial.target.dto.TargetDto;
import com.example.techtree.domain.financial.target.service.TargetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
public class TargetController {


    @PostMapping("/test")
    public String test(){
        TargetDto testDto = new TargetDto(
        );

        TargetService.test1(testDto);
        return "redirect:/member/login";
    }
}
