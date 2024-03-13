package com.example.techtree.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long member_id;

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

	private String role;

	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();

		if (this.role != null && this.role.equals("ADMIN")) {
			authorities.add(new SimpleGrantedAuthority("ADMIN"));
		}
		else {
			authorities.add(new SimpleGrantedAuthority("MEMBER"));
		}
		return authorities;
	}
}
