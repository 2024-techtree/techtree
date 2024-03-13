package com.example.techtree.domain.saving.goal.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

import com.example.techtree.domain.member.dao.MemberRepository;
import com.example.techtree.domain.saving.goal.dto.GoalDto;
import com.example.techtree.domain.saving.goal.entity.Goal;
import com.example.techtree.domain.saving.goal.service.GoalService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/saving/goal/")
public class GoalController {

	private final GoalService goalService;
	private final MemberRepository memberRepository;

	@GetMapping("/create")
	public String savingGoalCreate(Principal principal) {
		if (principal == null) {
			return "redirect:/member/login";
		}
		return "domain/saving/saving_goal_create";
	}

	@PostMapping("/create")
	public String savingGoalCreate(@ModelAttribute GoalDto goalDto, Principal principal) {
		if (principal == null) {
			return "redirect:/member/login";
		}
		String loginId = principal.getName();

		Long memberId = memberRepository.findByLoginId(loginId)
			.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + loginId))
			.getMemberId();

		Goal saveGoal = goalService.savingGoalCreate(goalDto, memberId);
		return "redirect:/saving/goal/detail/" + saveGoal.getSaving_goal_id();
	}

	@GetMapping("/detail/{saving_goal_id}")
	public String savingGoalDetail(@PathVariable Long saving_goal_id, Model model) {

		Goal goal = goalService.findGoalById(saving_goal_id);
		model.addAttribute("savingGoal", goal);
		return "domain/saving/saving_goal_detail";
	}

	@GetMapping("/list")
	public String savingGoalList(@RequestParam(name = "page", defaultValue = "1") int page, Model model,
		Principal principal) {
		if (principal == null) {
			return "redirect:/member/login";
		}

		String loginId = principal.getName();

		Long memberId = memberRepository.findByLoginId(loginId)
			.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + loginId))
			.getMemberId();

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

	@DeleteMapping("/delete/{saving_goal_id}")
	public ResponseEntity<?> deleteGoal(@PathVariable Long saving_goal_id) {
		try {
			goalService.deleteGoalById(saving_goal_id);
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
	public ResponseEntity<Map<String, String>> fetchGoalType(@RequestParam String goalName) {
		String goalType = goalService.getGoalType(goalName);

		Map<String, String> response = new HashMap<>();
		response.put("goalType", goalType);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/modify/{saving_goal_id}")
	public String savingGoalModifyPage(@PathVariable Long saving_goal_id, Model model) {
		Goal savingGoal = goalService.findGoalById(saving_goal_id);

		model.addAttribute("savingGoal", savingGoal);

		return "domain/saving/saving_goal_modify";
	}

	// 이 부분은 목표 수정을 처리하는 컨트롤러입니다.
	@PostMapping("/modify/{saving_goal_id}")
	public String savingGoalModify(@PathVariable Long saving_goal_id, @ModelAttribute GoalDto goalDto) {
		try {
			// 목표 수정 서비스를 호출하여 수정된 목표를 가져옵니다.
			Goal modifiedGoal = goalService.modifyGoal(saving_goal_id, goalDto);

			// 수정된 목표의 상세 페이지로 이동합니다.
			return "redirect:/saving/goal/detail/" + modifiedGoal.getSaving_goal_id();
		} catch (Exception e) {
			// 예외 발생 시 로그로 출력
			e.printStackTrace();
			// 에러 페이지 또는 적절한 처리를 여기에 추가할 수 있습니다.
			return "error"; // 예시로 에러 페이지로 리다이렉트
		}
	}
}
