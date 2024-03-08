package com.example.techtree.domain.member.controller;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCreateForm {


	@Size(min = 3, max = 25)
	@NotEmpty(message = "로그인ID는 필수항목입니다.")
	private String login_id;

	@NotEmpty(message = "비밀번호는 필수항목입니다.")
	private String password1;

	@NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
	private String password2;

	@NotEmpty(message = "이메일은 필수항목입니다.")
	@Email
	private String email;

	@NotEmpty(message = "이름은 필수항목입니다.")
	private String username;

	private LocalDate birthday;

	private String phoneNumber;

	private String profile;

	private String profileImage;
}
