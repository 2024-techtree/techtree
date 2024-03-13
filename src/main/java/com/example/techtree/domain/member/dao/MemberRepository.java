package com.example.techtree.domain.member.dao;

import java.util.Optional;

import com.example.techtree.domain.member.entity.SocialProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.techtree.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByLoginId(String username);
    Optional<Member> findByProviderAndProviderId(SocialProvider provider, String providerId);
    boolean existsByNickname(String nickname);
}
