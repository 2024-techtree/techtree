package com.example.techtree.domain.member.controller;

import com.example.techtree.domain.member.dao.MemberRepository;
import com.example.techtree.domain.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

	private final MemberService memberService;
	private final MemberRepository memberRepository;

	@GetMapping("/login")
	public String loginForm() {
		return "domain/member/login_form"; // 로그인 폼 페이지 반환
	}

	@GetMapping("/signup")
	public String signup(MemberCreateForm memberCreateForm, Model model) {

		return "domain/member/signup_form";
	}

	@PostMapping("/signup")
	public String signup(@ModelAttribute @Valid MemberCreateForm memberCreateForm, BindingResult bindingResult) {
		// 아이디 중복 검사
		if (memberRepository.findByLoginId(memberCreateForm.getLoginId()).isPresent()) {
			bindingResult.rejectValue("loginId", "loginId.exists", "이미 사용 중인 아이디입니다.");
			memberCreateForm.setLoginIdExists(true);
		}

		if (!memberCreateForm.getPassword1().equals(memberCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect",
				"비밀번호가 일치하지 않습니다.");
			return "domain/member/signup_form";

		}

		try {
			memberService.MemberCreate(memberCreateForm.getLoginId(), memberCreateForm.getUsername(),
				memberCreateForm.getPassword1(), memberCreateForm.getEmail(), memberCreateForm.getBirthday(),
				memberCreateForm.getPhoneNumber(), memberCreateForm.getProfile());
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
			return "domain/member/signup_form";
		} catch (Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", e.getMessage());
			return "domain/member/signup_form";
		}

		return "redirect:/";
	}
}
