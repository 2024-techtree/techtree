package com.example.techtree.global.security;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;

@Getter
public class SecurityUser extends User implements OAuth2User {
	private Long id;

	private String profileImage;

	public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities, long id, String profileImage) {
		super(username, password, authorities);
		this.id = id;
		this.profileImage = profileImage;
	}

	public SecurityUser(long id, String username, String password, String profileImage,
		Collection<? extends GrantedAuthority> authorities) {
		super(username, "", authorities);
		this.id = id;
		this.profileImage = profileImage; // 프로필 이미지 초기화
	}

	public SecurityUser(String username, String password, boolean enabled, boolean accountNonExpired,
		boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.id = id;
	}

	public Authentication genAuthentication() {
		Authentication auth = new UsernamePasswordAuthenticationToken(
			this,
			this.getPassword(),
			this.getAuthorities()
		);

		return auth;
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
