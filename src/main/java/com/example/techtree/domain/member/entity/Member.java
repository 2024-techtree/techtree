package com.example.techtree.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long member_id;

	private  String login_id;

	private String username; //가입한 사람의 이름. 동명이인 고려해 중복 해제

	private String password;

	@Column(unique = true)
	private String email;

	private LocalDate birthday;

	@Column(unique = true)
	private String phoneNumber;

	private String profile; // 프로필 경로

	private String profileImage; // 프로필 이미지 경로

}
