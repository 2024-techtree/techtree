package com.example.techtree.domain.member.dao;

import com.example.techtree.domain.member.entity.Member;
import com.example.techtree.domain.member.entity.SocialProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByProviderAndProviderId(SocialProvider provider, String providerId);
    boolean existsByNickname(String nickname);
}
