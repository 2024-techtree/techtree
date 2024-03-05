package com.example.techtree.domain.saving.record.controller;

import com.example.techtree.domain.saving.goal.entity.Goal;
import com.example.techtree.domain.saving.goal.service.GoalService;
import com.example.techtree.domain.saving.record.dto.RecordDto;
import com.example.techtree.domain.saving.record.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;
    private final GoalService goalService;
    @GetMapping("/test2")
    public String test2(Model model) {
        List<String> goalNames = goalService.getAllGoalNames();

        model.addAttribute("goalNames", goalNames);
        model.addAttribute("recordDto", new RecordDto());
        return "domain/record";
    }

    @PostMapping("/test2")
    public String test2(@ModelAttribute RecordDto recordDto) {
        recordService.test2(recordDto);
        return "redirect:/test2";
    }

    @GetMapping("/goal/type")
    public ResponseEntity<?> getGoalTypeByGoalName(@RequestParam String goalName) {
        System.out.println("Requested goal name: " + goalName); // 요청 받은 목표 이름 로깅
        Goal goal = goalService.findByGoalName(goalName);
        if (goal != null) {
            System.out.println("Found goal type: " + goal.getGoalType()); // 찾은 목표 유형 로깅
            return ResponseEntity.ok(goal.getGoalType());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
