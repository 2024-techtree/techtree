package com.example.techtree.domain.member.service;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.techtree.domain.member.dao.MemberRepository;
import com.example.techtree.domain.member.entity.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyPageService {

	private final MemberRepository memberRepository;

	public Member getLoggedInMember() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();

		return Optional.ofNullable(memberRepository.findByUsername(username))
			.orElseThrow(() -> new IllegalArgumentException("로그인한 사용자 정보를 찾을 수 없습니다."));
	}

	@Transactional
	public void updateMember(Member updatedMember) {
		// 업데이트된 정보로 회원 정보 업데이트
		Member existingMember = getLoggedInMember();
		existingMember.setEmail(updatedMember.getEmail());
		existingMember.setBirthday(updatedMember.getBirthday());
		existingMember.setPhoneNumber(updatedMember.getPhoneNumber());
		existingMember.setProfile(updatedMember.getProfile());
		existingMember.setProfileImage(updatedMember.getProfileImage());

		memberRepository.save(existingMember);
	}
}
