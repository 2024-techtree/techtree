package com.example.techtree.domain.member.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.techtree.domain.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/signup")
	public String signup(MemberCreateForm memberCreateForm, Model model) {

		return "signup_form";
	}

	@PostMapping("/signup")
	public String signup(@ModelAttribute MemberCreateForm memberCreateForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "signup_form";
		}

		if (!memberCreateForm.getPassword1().equals(memberCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect",
				"비밀번호가 일치하지 않습니다.");
			return "signup_form";

		}

		try {
			memberService.MemberCreate(memberCreateForm.getLogin_id(), memberCreateForm.getUsername(),
				memberCreateForm.getPassword1(), memberCreateForm.getEmail(), memberCreateForm.getBirthday(),
				memberCreateForm.getPhoneNumber(), memberCreateForm.getProfile(), memberCreateForm.getProfileImage());
		}
		catch(DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
			return "signup_form";
		}catch(Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", e.getMessage());
			return "signup_form";
		}


		return "redirect:/";
	}

}
