package com.example.techtree.domain.member.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class MemberUpdate {
	private String email;
	private LocalDate birthday;
	private String phoneNumber;
	private String profile;
	private String profileImage;


}
