package com.example.techtree.domain.saving.goal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.techtree.domain.saving.goal.dto.GoalDto;
import com.example.techtree.domain.saving.goal.entity.Goal;
import com.example.techtree.domain.saving.goal.entity.GoalStatus;
import com.example.techtree.domain.saving.goal.service.GoalService;
import com.example.techtree.global.security.SecurityUser;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/saving/goal/")
public class GoalController {
	private final GoalService goalService;

	@GetMapping("/create")
	public String savingGoalCreate(@AuthenticationPrincipal SecurityUser securityUser) {
		if (securityUser == null) {
			return "redirect:/member/login";
		}
		return "domain/saving/saving_goal_create";
	}

	@PostMapping("/create")
	public String savingGoalCreate(@ModelAttribute GoalDto goalDto,
		@AuthenticationPrincipal SecurityUser securityUser) {
		if (securityUser == null) {
			return "redirect:/member/login";
		}

		Long memberId = securityUser.getId();

		if (goalService.isDuplicateGoalName(goalDto.getGoalName(), memberId)) {
			// 여기서는 간단히 리다이렉트를 수행하지만, 실제로는 오류 메시지를 사용자에게 보여주어야 할 수 있습니다.
			// 예를 들어, RedirectAttributes를 사용하여 플래시 속성에 오류 메시지를 추가할 수 있습니다.
			return "redirect:/saving/goal/create?error=duplicate";
		}

		Goal saveGoal = goalService.savingGoalCreate(goalDto, memberId);
		return "redirect:/saving/goal/detail/" + saveGoal.getSavingGoalId();
	}

	@GetMapping("/detail/{savingGoalId}")
	public String savingGoalDetail(@PathVariable Long savingGoalId, Model model,
		@AuthenticationPrincipal SecurityUser securityUser) {
		if (securityUser == null) {
			return "redirect:/member/login";
		}
		Goal goal = goalService.findGoalById(savingGoalId);
		model.addAttribute("savingGoal", goal);
		return "domain/saving/saving_goal_detail";
	}

	@GetMapping("/list")
	public String savingGoalList(@RequestParam(name = "page", defaultValue = "1") int page, Model model,
		@AuthenticationPrincipal SecurityUser securityUser) {
		if (securityUser == null) {
			return "redirect:/member/login";
		}

		Long memberId = securityUser.getId();

		// 페이지 번호가 1 이하일 경우 0으로 설정
		int pageIndex = Math.max(page - 1, 0);

		Pageable pageable = PageRequest.of(pageIndex, 10);
		Page<Goal> savingGoalPage = goalService.findGoalsByMemberId(memberId, pageable);

		final int PAGE_BLOCK = 5;

		// 현재 페이지 그룹의 시작 페이지 계산 (1, 6, 11, ...)
		int startBlockPage = ((pageIndex) / PAGE_BLOCK) * PAGE_BLOCK + 1;

		// 현재 페이지 그룹의 끝 페이지 계산
		int endBlockPage = savingGoalPage.getTotalPages() > 0 ?
			Math.min(startBlockPage + PAGE_BLOCK - 1, savingGoalPage.getTotalPages()) : 1;

		model.addAttribute("goals", savingGoalPage.getContent()); // 페이징된 아이템 리스트
		model.addAttribute("startBlockPage", startBlockPage);
		model.addAttribute("endBlockPage", endBlockPage);
		model.addAttribute("currentPage", pageIndex + 1); // 현재 페이지 번호
		model.addAttribute("totalPages", savingGoalPage.getTotalPages()); // 전체 페이지 수
		return "domain/saving/saving_goal_list";
	}

	@DeleteMapping("/delete/{savingGoalId}")
	public ResponseEntity<?> deleteGoal(@PathVariable Long savingGoalId) {
		try {
			goalService.deleteGoalById(savingGoalId);
			// 성공적으로 삭제되었을 때 200 OK 상태 코드와 메시지 반환
			return ResponseEntity.ok("저축 목표가 성공적으로 삭제되었습니다.");
		} catch (Exception e) {
			// 오류 처리 (예: 삭제 대상이 없거나 데이터베이스 오류 등)
			// 실패했을 때 400 Bad Request 또는 적절한 상태 코드와 메시지 반환
			return ResponseEntity.badRequest().body("삭제를 완료할 수 없습니다.");
		}
	}

	@GetMapping("/fetchGoalType")
	@ResponseBody
	public ResponseEntity<Map<String, String>> fetchGoalType(@RequestParam String goalName,
		@AuthenticationPrincipal SecurityUser securityUser) {
		Long memberId = securityUser.getId();

		String goalType = goalService.getGoalType(goalName, memberId);

		Map<String, String> response = new HashMap<>();
		response.put("goalType", goalType);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/modify/{savingGoalId}")
	public String savingGoalModifyPage(@PathVariable Long savingGoalId, Model model,
		@AuthenticationPrincipal SecurityUser securityUser) {
		if (securityUser == null) {
			return "redirect:/member/login";
		}
		Goal savingGoal = goalService.findGoalById(savingGoalId);

		model.addAttribute("savingGoal", savingGoal);

		return "domain/saving/saving_goal_modify";
	}

	// 이 부분은 목표 수정을 처리하는 컨트롤러입니다.
	@PostMapping("/modify/{savingGoalId}")
	public String savingGoalModify(@PathVariable Long savingGoalId, @ModelAttribute GoalDto goalDto) {
		try {
			// 목표 수정 서비스를 호출하여 수정된 목표를 가져옵니다.
			Goal modifiedGoal = goalService.modifyGoal(savingGoalId, goalDto);

			// 수정된 목표의 상세 페이지로 이동합니다.
			return "redirect:/saving/goal/detail/" + modifiedGoal.getSavingGoalId();
		} catch (Exception e) {
			// 예외 발생 시 로그로 출력
			e.printStackTrace();
			// 에러 페이지 또는 적절한 처리를 여기에 추가할 수 있습니다.
			return "error"; // 예시로 에러 페이지로 리다이렉트
		}
	}

	@GetMapping("/dashboard")
	public String savingGoalDashboard(Model model, @AuthenticationPrincipal SecurityUser securityUser) {
		if (securityUser == null) {
			return "redirect:/member/login";
		}
		Long memberId = securityUser.getId();

		List<Goal> top5Goals = goalService.findTop5GoalsByMemberId(memberId);
		// "완료" 상태의 저축 목표 조회
		List<Goal> top5CompletedGoals = goalService.findTop5CompletedGoalsByMemberId(memberId, GoalStatus.COMPLETED);

		// Java 8 스트림을 사용하여 데이터 가공
		List<String> goalNames = top5Goals.stream().map(Goal::getGoalName).collect(Collectors.toList());
		List<Long> currentPrices = top5Goals.stream().map(Goal::getCurrentPrice).collect(Collectors.toList());
		List<Long> goalPrices = top5Goals.stream().map(Goal::getGoalPrice).collect(Collectors.toList());

		List<String> completedGoalNames = top5CompletedGoals.stream()
			.map(Goal::getGoalName)
			.collect(Collectors.toList());
		List<Long> completedCurrentPrices = top5CompletedGoals.stream()
			.map(Goal::getCurrentPrice)
			.collect(Collectors.toList());
		List<Long> completedGoalPrices = top5CompletedGoals.stream()
			.map(Goal::getGoalPrice)
			.collect(Collectors.toList());

		// 가공된 데이터를 모델에 추가
		model.addAttribute("goalNames", goalService.convertToJson(goalNames));
		model.addAttribute("currentPrices", goalService.convertToJson(currentPrices));
		model.addAttribute("goalPrices", goalService.convertToJson(goalPrices));
		model.addAttribute("currentPage", "dashboard");
		model.addAttribute("completedGoalNames", goalService.convertToJson(completedGoalNames));
		model.addAttribute("completedCurrentPrices", goalService.convertToJson(completedCurrentPrices));
		model.addAttribute("completedGoalPrices", goalService.convertToJson(completedGoalPrices));

		return "domain/mypage/dashboard";
	}

}
