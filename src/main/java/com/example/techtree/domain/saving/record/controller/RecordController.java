package com.example.techtree.domain.saving.record.controller;

import com.example.techtree.domain.saving.goal.service.GoalService;
import com.example.techtree.domain.saving.record.dto.RecordDto;
import com.example.techtree.domain.saving.record.entity.Record;
import com.example.techtree.domain.saving.record.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
	@GetMapping("/list")
	public String savingRecordList(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
		Pageable pageable = PageRequest.of(page - 1, 10); // 페이지 번호는 0부터 시작하므로 -1 해줍니다.
		Page<Record> recordPage = recordService.getAllRecords(pageable);

		final int PAGE_BLOCK = 5;

		// 현재 페이지 그룹의 시작 페이지 계산 (1, 6, 11, ...)
		int startBlockPage = ((page - 1) / PAGE_BLOCK) * PAGE_BLOCK + 1;

		// 현재 페이지 그룹의 끝 페이지 계산
		int endBlockPage = recordPage.getTotalPages() > 0 ?
				Math.min(startBlockPage + PAGE_BLOCK - 1, recordPage.getTotalPages()) : 1;

		model.addAttribute("records", recordPage.getContent()); // 페이징된 기록 리스트
		model.addAttribute("startBlockPage", startBlockPage);
		model.addAttribute("endBlockPage", endBlockPage);
		model.addAttribute("currentPage", page); // 현재 페이지 번호
		model.addAttribute("totalPages", recordPage.getTotalPages()); // 전체 페이지 수
		return "/domain/saving/record_list"; // 반환할 뷰의 이름을 올바르게 수정합니다.
	}


}
