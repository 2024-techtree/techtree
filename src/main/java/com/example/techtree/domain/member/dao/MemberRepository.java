package com.example.techtree.domain.member.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.techtree.domain.member.entity.Member;

public interface MemberRepository  extends JpaRepository<Member, Long> {
}
