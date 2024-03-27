package com.example.techtree.domain.saving.record.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.techtree.domain.member.dao.MemberRepository;
import com.example.techtree.domain.saving.goal.service.GoalService;
import com.example.techtree.domain.saving.record.dto.RecordDto;
import com.example.techtree.domain.saving.record.entity.Record;
import com.example.techtree.domain.saving.record.service.RecordService;
import com.example.techtree.global.security.SecurityUser;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/saving/record/")
public class RecordController {

	private final RecordService recordService;
	private final GoalService goalService;
	private final MemberRepository memberRepository;

	@GetMapping("/create")
	public String savingRecordCreate(Model model, @AuthenticationPrincipal SecurityUser securityUser) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName(); // 현재 사용자의 아이디
		// 사용자가 로그인하지 않은 경우 로그인 페이지로 리다이렉트합니다.
		if (securityUser == null) {
			return "redirect:/member/login";
		}

		Long memberId = securityUser.getId();

		// 사용자가 로그인한 경우 목표 목록을 가져와서 뷰에 전달합니다.
		List<String> goalNames = goalService.getAllGoalNames(memberId);
		model.addAttribute("goalNames", goalNames);
		model.addAttribute("recordDto", new RecordDto());

		return "domain/saving/record_create";
	}

	// RecordController
	@PostMapping("/create")
	public String savingRecordCreate(@ModelAttribute RecordDto recordDto,
		@AuthenticationPrincipal SecurityUser securityUser) {
		if (securityUser == null) {
			return "redirect:/member/login";
		}

		Long memberId = securityUser.getId();

		Record saveRecord = recordService.savingRecordCreate(recordDto, memberId);

		return "redirect:/saving/goal/list";
	}

	@GetMapping("/goal/{goalId}/records")
	public String savingRecordListByGoal(@PathVariable Long goalId,
		@RequestParam(name = "page", defaultValue = "1") int page, Model model,
		@AuthenticationPrincipal SecurityUser securityUser) {
		if (securityUser == null) {
			return "redirect:/member/login";
		}

		Pageable pageable = PageRequest.of(page - 1, 10);
		Page<Record> recordPage = recordService.getRecordsByGoalId(goalId, pageable);

		final int PAGE_BLOCK = 5;
		int startBlockPage = ((page - 1) / PAGE_BLOCK) * PAGE_BLOCK + 1;
		int endBlockPage = recordPage.getTotalPages() > 0 ?
			Math.min(startBlockPage + PAGE_BLOCK - 1, recordPage.getTotalPages()) : 1;

		model.addAttribute("records", recordPage.getContent());
		model.addAttribute("startBlockPage", startBlockPage);
		model.addAttribute("endBlockPage", endBlockPage);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", recordPage.getTotalPages());
		model.addAttribute("goalId", goalId); // 목표 ID도 모델에 추가

		return "domain/saving/record_list";
	}

	@GetMapping("/list")
	public String savingRecordAllListByGoal(Long goalId,
		@RequestParam(name = "page", defaultValue = "1") int page, Model model,
		@AuthenticationPrincipal SecurityUser securityUser) {
		if (securityUser == null) {
			return "redirect:/member/login";
		}

		Long memberId = securityUser.getId();

		Pageable pageable = PageRequest.of(page - 1, 10);
		Page<Record> recordPage = recordService.getRecordsByMemberId(memberId, pageable);

		final int PAGE_BLOCK = 5;
		int startBlockPage = ((page - 1) / PAGE_BLOCK) * PAGE_BLOCK + 1;
		int endBlockPage = recordPage.getTotalPages() > 0 ?
			Math.min(startBlockPage + PAGE_BLOCK - 1, recordPage.getTotalPages()) : 1;

		model.addAttribute("records", recordPage.getContent());
		model.addAttribute("startBlockPage", startBlockPage);
		model.addAttribute("endBlockPage", endBlockPage);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", recordPage.getTotalPages());
		model.addAttribute("goalId", goalId); // 목표 ID도 모델에 추가

		return "domain/saving/record_list";
	}

}
