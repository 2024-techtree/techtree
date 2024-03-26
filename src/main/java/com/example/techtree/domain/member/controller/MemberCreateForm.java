package com.example.techtree.domain.member.controller;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class MemberCreateForm {

	@Size(min = 3, max = 25, message = "로그인ID는 3자 이상 25자 이하여야 합니다.")
	@NotEmpty(message = "로그인ID는 필수항목입니다.")
	private String loginId;

	@NotEmpty(message = "비밀번호는 필수항목입니다.")
	private String password1;

	@NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
	private String password2;

	@NotEmpty(message = "이메일은 필수항목입니다.")
	@Email(message = "올바른 이메일 주소를 입력해주세요.")
	private String email;

	@NotEmpty(message = "이름은 필수항목입니다.")
	private String username;

	private LocalDate birthday;

	private String phoneNumber;

	private String profile;

	private String profileImage;

	@Getter
	private boolean loginIdExists;

	public void setLoginIdExists(boolean loginIdExists) {
		this.loginIdExists = loginIdExists;
	}
}
