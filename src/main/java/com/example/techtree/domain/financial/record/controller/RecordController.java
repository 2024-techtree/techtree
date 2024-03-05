package com.example.techtree.domain.financial.record.controller;

import com.example.techtree.domain.financial.record.dto.RecordDto;
import com.example.techtree.domain.financial.record.service.RecordService;
import com.example.techtree.domain.financial.target.service.TargetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;
    private final TargetService targetService;
    @GetMapping("/test2")
    public String test2(Model model) {
        List<String> targetNames = targetService.getAllTargetNames();

        model.addAttribute("targetNames", targetNames);
        model.addAttribute("recordDto", new RecordDto());
        return "domain/record";
    }

    @PostMapping("/test2")
    public String test2(@ModelAttribute RecordDto recordDto) {
        recordService.test2(recordDto);
        return "redirect:/test2";
    }
}
