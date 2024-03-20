package com.example.techtree.domain.member.controller;

import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.techtree.domain.member.dto.MemberUpdate;
import com.example.techtree.domain.member.entity.Member;
import com.example.techtree.domain.member.service.MyPageService;
import com.example.techtree.global.security.SecurityUser;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member/mypage")
public class MypageController {

	private final MyPageService myPageService;

	@GetMapping("")
	public String showMyPage(@AuthenticationPrincipal SecurityUser user, Model model) {
		 //Optional<Member> loggedInMember = myPageService.getLoggedInMember();
		 MemberUpdate memberUpdate = myPageService.getProfile(user.getId());
		model.addAttribute("member", memberUpdate);
		return "domain/member/myPage";
	}

	@PostMapping("")
	public String updateMyPage(@ModelAttribute MemberUpdate updatedMember, Model model) {
		try {
			myPageService.updateMember(updatedMember);
			model.addAttribute("successMessage", "회원정보 수정이 완료되었습니다.");
		} catch (IllegalArgumentException e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/member/myPage/";
	}
}
