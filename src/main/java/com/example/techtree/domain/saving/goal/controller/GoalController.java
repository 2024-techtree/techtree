package com.example.techtree.domain.saving.goal.controller;

import com.example.techtree.domain.saving.goal.dto.GoalDto;
import com.example.techtree.domain.saving.goal.entity.Goal;
import com.example.techtree.domain.saving.goal.service.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/saving/goal/")
public class GoalController {

	private final GoalService goalService;

	@GetMapping("/create")
	public String savingGoalCreate() {

		return "domain/saving/saving_goal_create";
	}

	@PostMapping("/create")
	public String savingGoalCreate(@ModelAttribute GoalDto goalDto) {
		Goal saveGoal = goalService.savingGoalCreate(goalDto);
		return "redirect:/saving/goal/detail/" + saveGoal.getSaving_goal_id();
	}

	@GetMapping("/detail/{saving_goal_id}")
	public String savingGoalDetail(@PathVariable Long saving_goal_id, Model model) {

		Goal goal = goalService.findGoalById(saving_goal_id);
		model.addAttribute("savingGoal", goal);
		return "domain/saving/saving_goal_detail";
	}

	@GetMapping("/list")
	public String savingGoalList(Model model) {
		List<Goal> goals = goalService.getAllPosts();
		model.addAttribute("goals", goals);
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