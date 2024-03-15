package com.example.techtree.domain.member.controller;

import static com.example.techtree.domain.member.entity.SocialProvider.*;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.*;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.techtree.domain.member.dto.KakaoUserInfo;
import com.example.techtree.domain.member.entity.Member;
import com.example.techtree.domain.member.service.KakaoService;
import com.example.techtree.global.security.SecurityUser;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/kakao")
public class KakaoController {

	private final KakaoService ks;

	@GetMapping("/do")
	public String loginPage() {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println("name = " + name);
		return "domain/member/login_form";
	}

	@GetMapping("/callback")
	public String getCI(@RequestParam String code, Model model, HttpServletRequest request,
		HttpServletResponse response) throws IOException {
		System.out.println("code = " + code);
		String access_token = ks.getToken(code);
		KakaoUserInfo userInfo = ks.getUserInfo(access_token);
		System.out.println("카카오 아이디 : " + userInfo.getId());
		System.out.println("카카오 유저네임 : " + userInfo.getNickname());
		System.out.println("카카오 이미지 네임 : " + userInfo.getProfileImg());
		model.addAttribute("code", code);
		model.addAttribute("access_token", access_token);
		model.addAttribute("userInfo", userInfo);
		System.out.println("userInfo = " + userInfo);

		Member member = ks.login(userInfo);

		SecurityUser securityUser = new SecurityUser(member.getMemberId(), member.getLoginId(), "",
			member.getAuthorities());

		Authentication authentication =
			new OAuth2AuthenticationToken(securityUser, securityUser.getAuthorities(), KAKAO.toString());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		// 새로운 세션 생성
		session = request.getSession(true);
		session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

		// 세션 ID를 쿠키에 설정
		Cookie cookie = new Cookie("JSESSIONID", session.getId());
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		response.addCookie(cookie);

		//ci는 비즈니스 전환후 검수신청 -> 허락받아야 수집 가능
		return "redirect:/";
	}
}
