package com.example.techtree.domain.member.service;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.techtree.domain.member.dao.MemberRepository;
import com.example.techtree.domain.member.dto.MyPage;
import com.example.techtree.domain.member.entity.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyPageService {

	private final MemberRepository memberRepository;

	public Optional<Member> getLoggedInMember() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();

		return Optional.ofNullable(memberRepository.findByLoginId(username))
			.orElseThrow(() -> new IllegalArgumentException("로그인한 사용자 정보를 찾을 수 없습니다."));
	}

	@Transactional
	public void updateMember(MyPage myPage, Long id) {
		Optional<Member> optionalMember = memberRepository.findById(id);
		Member member = optionalMember.orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));


		// 새로운 값을 입력한 경우에만 업데이트
		if (myPage.getEmail() != null) {
			member.setEmail(myPage.getEmail());
		}
		if (myPage.getBirthday() != null) {
			member.setBirthday(myPage.getBirthday());
		}
		if (myPage.getPhoneNumber() != null) {
			member.setPhoneNumber(myPage.getPhoneNumber());
		}
		if (myPage.getProfileImage() != null) {
			member.setProfileImage(myPage.getProfileImage());
		}

		// 변경된 회원 정보를 저장
		memberRepository.save(member);
	}


	public MyPage getProfile(long id) {
		Optional<Member> optMember = memberRepository.findByMemberId(id);
		Member member = optMember.orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));
		return new MyPage(
			member.getUsername(),
			"", // 비밀번호는 가져오지 않음
			"", // 비밀번호 확인용 필드는 기본값으로 설정
			member.getEmail(),
			member.getBirthday(),
			member.getPhoneNumber(),
			member.getProfile()
		);
	}
}
