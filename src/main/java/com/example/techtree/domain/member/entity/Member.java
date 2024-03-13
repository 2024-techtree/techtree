package com.example.techtree.domain.member.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.techtree.domain.saving.goal.entity.Goal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;

	private String loginId;

	private String username; //가입한 사람의 이름. 동명이인 고려해 중복 해제

	private String password;

	@Column(unique = true)
	private String email;

	private LocalDate birthday;

	@Column(unique = true)
	private String phoneNumber;

	private String profile; // 프로필 경로

	private String profileImage; // 프로필 이미지 경로

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Goal> goals = new ArrayList<>();
}
