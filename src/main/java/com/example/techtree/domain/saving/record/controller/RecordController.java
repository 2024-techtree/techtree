package com.example.techtree.domain.saving.record.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.techtree.domain.saving.goal.service.GoalService;
import com.example.techtree.domain.saving.record.dto.RecordDto;
import com.example.techtree.domain.saving.record.service.RecordService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/saving/record/")
public class RecordController {

	private final RecordService recordService;
	private final GoalService goalService;

	@GetMapping("/create")
	public String savingRecordCreate(Model model) {
		List<String> goalNames = goalService.getAllGoalNames();

		model.addAttribute("goalNames", goalNames);
		model.addAttribute("recordDto", new RecordDto());
		return "/domain/saving/record_create";
	}

	// RecordController
	@PostMapping("/create")
	public String savingRecordCreate(@ModelAttribute RecordDto recordDto) {
		recordService.savingRecordCreate(recordDto);
		return "redirect:/saving/goal/list";
	}
}
