package com.example.techtree.domain.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.techtree.domain.member.entity.Member;
import com.example.techtree.domain.member.service.MyPageService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member/myPage")
public class MypageController {

	private final MyPageService myPageService;

	@GetMapping
	public String showMyPage(Model model) {
		Member loggedInMember = myPageService.getLoggedInMember();
		model.addAttribute("member", loggedInMember);
		return "domain/member/myPage";
	}

	@PostMapping
	public String updateMyPage(@ModelAttribute Member updatedMember, Model model) {
		try {
			myPageService.updateMember(updatedMember);
			model.addAttribute("successMessage", "회원정보 수정이 완료되었습니다.");
		} catch (IllegalArgumentException e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/member/myPage/";
	}
}
