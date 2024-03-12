package com.example.techtree.domain.member.service;

import java.time.LocalDate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.techtree.domain.member.dao.MemberRepository;
import com.example.techtree.domain.member.entity.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
//@Transactional(readOnly = true)
@Service
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public Member MemberCreate (String login_id,String username, String password, String email, LocalDate birthday, String phoneNumber, String profile, String profileImage) {
		validateInput(username, password, email);

		Member member = new Member();
		member.setLogin_id(login_id);
		member.setUsername(username);
		member.setPassword(passwordEncoder.encode(password));
		member.setEmail(email);
		member.setBirthday(birthday);
		 member.setPhoneNumber(phoneNumber);
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

}
