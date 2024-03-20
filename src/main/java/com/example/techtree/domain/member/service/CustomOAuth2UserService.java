package com.example.techtree.domain.member.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.techtree.domain.member.dao.MemberRepository;
import com.example.techtree.domain.member.dto.OAuthAttributes;
import com.example.techtree.domain.member.dto.SessionUser;
import com.example.techtree.domain.member.entity.Member;
import com.example.techtree.global.security.SecurityUser;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service

@RequiredArgsConstructor

public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final MemberRepository memberRepository;
	private final HttpSession httpSession;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		// OAuth2 인증 정보로부터 사용자 정보 추출
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		String userNameAttributeName = userRequest.getClientRegistration()
			.getProviderDetails()
			.getUserInfoEndpoint()
			.getUserNameAttributeName();
		OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
			oAuth2User.getAttributes());

		// 사용자 정보를 바탕으로 Member 엔티티 업데이트 또는 생성
		Member member = saveOrUpdate(attributes);
		System.out.println("google member = " + member.getUsername());

		// SecurityUser 인스턴스 생성
		SecurityUser securityUser = new SecurityUser(
			member.getMemberId(),
			member.getUsername(),
			"", // 패스워드는 보통 null로 설정하며, OAuth2에서는 사용되지 않음
			member.getProfileImage(),
			member.getEmail(),
			oAuth2User.getAuthorities()
		);

		// Authentication 객체 생성 및 SecurityContext에 저장
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
			securityUser,
			null,
			securityUser.getAuthorities()
		);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// 세션에 사용자 정보 저장 (옵션)
		httpSession.setAttribute("user", new SessionUser(member));

		return securityUser; // SecurityUser 반환
	}

	private Member saveOrUpdate(OAuthAttributes attributes) {
		Member member = memberRepository.findByLoginIdAndProvider(attributes.getEmail(), attributes.getProvider())
			.map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
			.orElse(attributes.toEntity());

		System.out.println("attributes = " + attributes.getName() + " 슬래쉬 " + attributes.getPicture() + " 슬래쉬 "
			+ attributes.getEmail());
		System.out.println(
			"member = " + member.getUsername() + " 슬래쉬 " + member.getProfileImage() + " 슬래쉬 " + member.getEmail());
		return memberRepository.save(member);

	}

}
