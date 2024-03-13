package com.example.techtree.domain.member.service;

import java.time.LocalDate;
import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.techtree.domain.member.dao.MemberRepository;
import com.example.techtree.domain.member.entity.Member;
import com.example.techtree.domain.member.entity.SocialProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
//@Transactional(readOnly = true)
@Service
public class MemberService implements UserDetailsService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public Member MemberCreate(String loginId, String username, String password, String email, LocalDate birthday,
		String phoneNumber, String profile) {
		validateInput(username, password, email);

		Member member = new Member();
		member.setLoginId(loginId);
		member.setUsername(username);
		member.setPassword(passwordEncoder.encode(password));
		member.setEmail(email);
		member.setBirthday(birthday);
		// member.setPhoneNumber(phoneNumber);
		// member.setProfileImage(profileImage);

		this.memberRepository.save(member);
		return member;
	}

	private void validateInput(String username, String password, String email) {
		validateUsername(username);
		validatePassword(password);
		validateEmail(email);
	}

	private void validateUsername(String username) {
		if (username == null) {
			throw new IllegalArgumentException("사용자 이름을 입력해주세요.");
		}
	}

	private void validatePassword(String password) {
		if (password == null) {
			throw new IllegalArgumentException("비밀번호를 입력해주세요.");
		}
	}

	private void validateEmail(String email) {
		if (email == null) {
			throw new IllegalArgumentException("이메일을 입력해주세요.");
		}
	}

	public Optional<Member> findByProviderAndProviderId(SocialProvider provider, String providerId) {
		return memberRepository.findByProviderAndProviderId(provider, providerId);
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findByLoginId(username)
			.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
		return new User(member.getLoginId(), member.getPassword(), Collections.emptyList());
	}
}
