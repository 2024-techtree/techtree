package com.example.techtree.global.security;

import com.example.techtree.domain.member.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	@Autowired
	private final CustomOAuth2UserService customOAuth2UserService;
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeRequests(authorizeRequests ->
				authorizeRequests
					.requestMatchers("/user/mypage/**")
					.authenticated()
					.anyRequest()
					.permitAll()
			)
			.exceptionHandling(exceptionHandling ->
				exceptionHandling
					.authenticationEntryPoint((request, response, authException) ->
						response.sendRedirect("/member/login")) // 인증되지 않은 사용자를 로그인 페이지로 리다이렉트
			)
			.sessionManagement(session -> session
				.sessionFixation()
				.migrateSession() // 세션 고정 공격 방지
				.invalidSessionUrl("/member/auth-result")
				.maximumSessions(1)
				.expiredUrl("/member/auth-result?failMsg=" + URLEncoder.encode("비정상적인 접근입니다.", StandardCharsets.UTF_8))
			)
			.csrf(
				csrf ->
					csrf.ignoringRequestMatchers(
						"**", //모든 post요청에 csrf토큰 심고나서 삭제
						"/oauth2/**" //소셜 로그인시 토큰과 사용자정보 받을 수 있도록
					)
			)
			.headers(
				headers ->
					headers
						.frameOptions(
							HeadersConfigurer.FrameOptionsConfig::sameOrigin
						)
			)
			.formLogin(
				(formLogin) ->
					formLogin
						.loginPage("/member/login")
						.loginProcessingUrl("/member/login") // 로그인 처리 경로
						.usernameParameter("loginId") // 폼에서 사용하는 아이디 필드 이름
						.passwordParameter("password") // 폼에서 사용하는 비밀번호 필드 이름
						.defaultSuccessUrl("/") // 로그인 성공 시 리디렉션 경로
						.failureUrl("/member/login?error=true") // 로그인 실패 시 리디렉션 경로
						.permitAll()
			)
			.logout((logout) -> logout
				.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) // 로그아웃 처리 URL
				.logoutSuccessUrl("/") // 로그아웃 성공 후 리다이렉트할 URL
				.invalidateHttpSession(true) // 세션 무효화
				.deleteCookies("JSESSIONID") // 쿠키 삭제
				.permitAll()
			).oauth2Login(oauth2 -> oauth2
						.userInfoEndpoint(userInfo -> userInfo
								.userService(customOAuth2UserService)));

		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
