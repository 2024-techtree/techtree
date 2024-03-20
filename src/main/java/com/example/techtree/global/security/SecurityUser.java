package com.example.techtree.global.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class SecurityUser extends User implements OAuth2User {
	private Long id;

	private String profileImage;

	public SecurityUser(long id, String username, String password, String profileImage,
		Collection<? extends GrantedAuthority> authorities) {
		super(username, "", authorities);
		this.id = id;
		this.profileImage = profileImage; // 프로필 이미지 초기화
	}

	@Override
	public Map<String, Object> getAttributes() {
		return Map.of();
	}

	@Override
	public String getName() {
		return getUsername();
	}
}
