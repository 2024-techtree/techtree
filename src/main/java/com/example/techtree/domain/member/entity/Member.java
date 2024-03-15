package com.example.techtree.domain.member.entity;
import com.example.techtree.domain.saving.goal.entity.Goal;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static jakarta.persistence.EnumType.STRING;
@Getter
@Setter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;
	private String loginId;
	private String username; //가입한 사람의 이름. 동명이인 고려해 중복 해제
	private String password;
	/*@Column(unique = true)*/
	private String email;
	private LocalDate birthday;
	@Column(unique = true)
	private String phoneNumber;
	private String profile; // 프로필 경로
	private String profileImage; // 프로필 이미지 경로
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Goal> goals = new ArrayList<>();
	@Enumerated(STRING)
	private SocialProvider provider;    // 카카오 기준으로 값 넣기, 없다면 null
	private String providerId;  // 소셜 전용 ID 변수

//	@Enumerated(STRING)	// Constraint825 에 대한 에러 부분
	@Column
	private Role role;

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

	public Member update(String name, String picture) {
		this.username = name;
		this.profileImage = picture;
		this.role = Role.USER;

		return this;
	}
}