package com.example.techtree.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import com.example.techtree.domain.member.dao.MemberRepository;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MyPage {
	private String username;
	// @Length(min=8, max=16, message="비밀번호는 8자 이상, 16자 이하로 입력해주세요")
	private String password;
	private String confirmPassword; // 비밀번호 확인용 필드 추가
	private String email;
	private LocalDate birthday;
	private String phoneNumber;
	private String profileImage;
}

