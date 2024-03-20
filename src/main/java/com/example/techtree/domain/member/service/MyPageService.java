package com.example.techtree.domain.member.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.techtree.domain.member.dao.MemberRepository;
import com.example.techtree.domain.member.dto.MemberUpdate;
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
	public void updateMember(MemberUpdate updatedMember) {
		Optional<Member> optionalExistingMember = getLoggedInMember();
		Member existingMember = optionalExistingMember.orElseThrow(() -> new IllegalStateException("로그인한 사용자 정보를 찾을 수 없습니다."));
		existingMember.setEmail(updatedMember.getEmail());
		existingMember.setBirthday(updatedMember.getBirthday());
		existingMember.setPhoneNumber(updatedMember.getPhoneNumber());
		existingMember.setProfile(updatedMember.getProfile());
		existingMember.setProfileImage(updatedMember.getProfileImage());
		memberRepository.save(existingMember);
	}

	public MemberUpdate getProfile(long id) {
		Optional<Member> optMember = memberRepository.findByMemberId(id);
		Member member = optMember.get();
		return new MemberUpdate(
			member.getEmail(),
			member.getBirthday(),
			member.getPhoneNumber(),
			member.getProfile(),
			member.getProfileImage()
		);
	}
}
