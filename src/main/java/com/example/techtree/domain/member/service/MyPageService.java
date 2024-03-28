package com.example.techtree.domain.member.service;

import com.example.techtree.domain.member.dao.MemberRepository;
import com.example.techtree.domain.member.dto.MyPage;
import com.example.techtree.domain.member.entity.Member;
import com.example.techtree.global.rsData.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyPageService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public Optional<Member> getLoggedInMember() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();

		return Optional.ofNullable(memberRepository.findByLoginId(username))
			.orElseThrow(() -> new IllegalArgumentException("로그인한 사용자 정보를 찾을 수 없습니다."));
	}

	@Transactional
	public void updateMember(MyPage myPage, Long id) {
		Member member = memberRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException("User not found"));

		if (!myPage.getPassword().equals(myPage.getConfirmPassword())) { // 비밀번호 일치 확인
			throw new IllegalStateException("비밀번호가 일치하지않습니다.");
		}
		String encryptedPassword = passwordEncoder.encode(myPage.getPassword());
		member.setPhoneNumber(myPage.getPhoneNumber());
		member.setPassword(encryptedPassword);
		// 변경된 회원 정보를 저장
		memberRepository.save(member);
	}


	public Member getProfile(long id) {
		Member member = memberRepository.findByMemberId(id)
				.orElseThrow(() -> new DataNotFoundException("User not found"));;

		return member;
	}
}
