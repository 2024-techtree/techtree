package com.example.techtree.domain.financial.target.controller;

import com.example.techtree.domain.financial.target.dto.TargetDto;
import com.example.techtree.domain.financial.target.service.TargetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TargetController {

 private final TargetService targetService;
    @PostMapping("/test")
    public String test(@ModelAttribute TargetDto testDto) {
        targetService.test1(testDto);
        return "redirect:/domain/target";
    }
    @GetMapping("/test")
    public String test2(){
    

        return "domain/target";
    }
}
