package com.example.techtree.domain.member.controller;

import com.example.techtree.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	@GetMapping("/findid") // 아이디 찾기 페이지를 보여주는 핸들러
	public String findIdForm() {
		return "domain/member/findid"; // 아이디 찾기 폼 페이지 반환
	}

	@PostMapping("/findid") // 아이디를 찾는 핸들러
	public ModelAndView findMemberId(@RequestParam String username, @RequestParam String email, @RequestParam String phoneNumber, ModelAndView modelAndView) {
		memberService.findLoginIdByUsernameEmailAndPhoneNumber(username, email, phoneNumber).ifPresentOrElse(
				loginId -> modelAndView.addObject("message", "당신의 아이디는 " + loginId + " 입니다."),
				() -> modelAndView.addObject("message", "해당 정보와 일치하는 사용자가 없습니다.")
		);
		modelAndView.setViewName("domain/member/findIdResult"); // 결과를 보여줄 뷰의 이름
		return modelAndView; // ModelAndView 객체 반환
	}

	// 비밀번호 찾기 폼을 보여주는 핸들러
	@GetMapping("/findpassword")
	public String findPasswordForm() {
		return "domain/member/findpassword"; // 비밀번호 찾기 폼 페이지 반환
	}

	// 비밀번호를 찾는 핸들러
	@PostMapping("/findpassword")
	public String findPassword(@RequestParam String loginId, @RequestParam String username,
							   @RequestParam String email, @RequestParam String phoneNumber) {
		if (memberService.validateUser(loginId, username, email, phoneNumber)) {
			return "redirect:/member/resetpassword?loginId="+loginId; // 사용자가 검증되면 비밀번호 재설정 페이지로 이동
		} else {
			return "redirect:/member/findpassword?error=true"; // 사용자가 검증되지 않으면 다시 비밀번호 찾기 페이지로 이동
		}
	}

	// 새 비밀번호 입력 폼을 보여주는 핸들러
	@GetMapping("/resetpassword")
	public String resetPasswordForm(@RequestParam String loginId, Model model) {
		model.addAttribute("loginId", loginId);
		return "domain/member/resetpassword"; // 새 비밀번호 입력 폼 페이지 반환
	}

	// 새 비밀번호를 입력받아 변경하는 핸들러
	@PostMapping("/resetpassword")
	public String updatePassword(@RequestParam String loginId, @RequestParam String newPassword,
								 @RequestParam String confirmPassword, RedirectAttributes redirectAttributes) {
		// 새 비밀번호와 확인 비밀번호가 일치하는지 확인
		if (!newPassword.equals(confirmPassword)) {
			redirectAttributes.addFlashAttribute("errorMessage", "새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
			return "redirect:/member/resetpassword"; // 새 비밀번호 입력 페이지로 다시 이동
		}

		// 새 비밀번호로 변경하는 로직 실행
		try {
			memberService.updatePassword(loginId, newPassword);
			redirectAttributes.addFlashAttribute("successMessage", "새 비밀번호로 변경되었습니다.");
			return "redirect:/member/login"; // 로그인 페이지로 이동
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "비밀번호 변경 중 오류가 발생했습니다.");
			return "redirect:/member/resetpassword"; // 비밀번호 변경 페이지로 다시 이동
		}
	}
}
