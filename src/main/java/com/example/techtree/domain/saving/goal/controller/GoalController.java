package com.example.techtree.domain.saving.goal.controller;

import com.example.techtree.domain.saving.goal.dto.GoalDto;
import com.example.techtree.domain.saving.goal.entity.Goal;
import com.example.techtree.domain.saving.goal.service.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/saving")
public class GoalController
{

 private final GoalService goalService;

    @GetMapping("/create")
    public String savingGoalCreate(){

        return "domain/saving/saving_goal_create";
    }
    @PostMapping("/create")
    public String savingGoalCreate(@ModelAttribute GoalDto goalDto) {
        Goal saveGoal = goalService.savingGoalCreate(goalDto);
        return "redirect:/saving/detail/" + saveGoal.getSaving_goal_id();
    }

    @GetMapping("/detail/{saving_goal_id}")
    public String savingGoalDetail(@PathVariable Long saving_goal_id, Model model){

        Goal goal = goalService.findGoalById(saving_goal_id);
        model.addAttribute("savingGoal", goal);
        return "domain/saving/saving_goal_detail";
    }

}
