package com.example.techtree.domain.member.controller;

import com.example.techtree.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/login")
	public String loginForm() {
		return "domain/member/login_form"; // 로그인 폼 페이지 반환
	}

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
			memberService.MemberCreate(memberCreateForm.getLoginId(), memberCreateForm.getUsername(),
				memberCreateForm.getPassword1(), memberCreateForm.getEmail(), memberCreateForm.getBirthday(),
				memberCreateForm.getPhoneNumber(), memberCreateForm.getProfile());
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
			return "signup_form";
		} catch (Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", e.getMessage());
			return "signup_form";
		}

		return "redirect:/";
	}

	@GetMapping("/member/findid")
	public ModelAndView findMemberId(@RequestParam String username, @RequestParam String email, @RequestParam String phoneNumber, ModelAndView modelAndView) {
		memberService.findLoginIdByUsernameEmailAndPhoneNumber(username, email, phoneNumber).ifPresentOrElse(
				loginId -> modelAndView.addObject("message", "당신의 아이디는 " + loginId + " 입니다."),
				() -> modelAndView.addObject("message", "해당 정보와 일치하는 사용자가 없습니다.")
		);
		modelAndView.setViewName("findIdResult"); // 결과를 보여줄 뷰의 이름
		return modelAndView; // ModelAndView 객체 반환
	}
}
