package com.example.techtree.domain.member.dao;

import com.example.techtree.domain.member.entity.Member;
import com.example.techtree.domain.member.entity.SocialProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByLoginId(String loginId);

    Optional<Member> findByLoginIdAndProvider(String loginId, SocialProvider provider);

    Optional<Member> findByUsername(String name);
}
