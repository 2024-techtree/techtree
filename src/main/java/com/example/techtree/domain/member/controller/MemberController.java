package com.example.techtree.domain.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.techtree.domain.member.service.MemberService;

import jakarta.validation.Valid;

@Controller
 @RequestMapping("/member")
 public class MemberController {

	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping("/signup")
	public String signup(MemberCreateForm memberCreateForm) {
		return "signup_form";
	}

	@PostMapping("/signup")
	public String signup(@Valid MemberCreateForm memberCreateForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "signup_form";
		}

		if (!memberCreateForm.getPassword1().equals(memberCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect",
				"패스워드가 일치하지 않습니다.");
			return "signup_form";
		}

		memberService.createMember(memberCreateForm.getUsername(), memberCreateForm.getPassword1(), memberCreateForm.getEmail(), memberCreateForm.getBirthday(), memberCreateForm.getPhoneNumber(), memberCreateForm.getProfile(), memberCreateForm.getProfileImage());




		return "redirect:/";
	}

}



