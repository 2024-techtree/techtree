package com.example.techtree.domain.member.controller;

import com.example.techtree.domain.member.dto.MyPage;
import com.example.techtree.domain.member.entity.Member;
import com.example.techtree.domain.member.service.MyPageService;
import com.example.techtree.global.security.SecurityUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member/mypage")
public class MypageController {

	private final MyPageService myPageService;

	@GetMapping
	public String showMyPage(@AuthenticationPrincipal SecurityUser user, Model model) {
		Member member = myPageService.getProfile(user.getId());
		model.addAttribute("member", member);
		return "domain/member/myPage";
	}

	@PostMapping
	public String updateMyPage(@Valid @ModelAttribute MyPage myPage, BindingResult bindingResult, Model model,
							   @AuthenticationPrincipal SecurityUser securityUser) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("myPage", myPage);
		return "domain/member/myPage"; // 유효성 검사 오류가 있는 페이지로 다시 이동
	}

		try {
			model.addAttribute("myPage", myPage);
			myPageService.updateMember(myPage, securityUser.getId());
			model.addAttribute("successMessage", "회원정보 수정이 완료되었습니다.");
		} catch (IllegalArgumentException e) {
			model.addAttribute("myPage", myPage);
			model.addAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/";
	}

}
