package com.example.techtree.domain.saving.goal.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.techtree.domain.saving.goal.dto.GoalDto;
import com.example.techtree.domain.saving.goal.entity.Goal;
import com.example.techtree.domain.saving.goal.service.GoalService;

import lombok.RequiredArgsConstructor;

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
}
