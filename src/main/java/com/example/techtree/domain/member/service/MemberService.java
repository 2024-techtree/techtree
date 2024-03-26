package com.example.techtree.domain.member.service;

import com.example.techtree.domain.member.dao.MemberRepository;
import com.example.techtree.domain.member.entity.Member;
import com.example.techtree.domain.member.entity.Role;
import com.example.techtree.domain.member.entity.SocialProvider;
import com.example.techtree.global.rsData.DataNotFoundException;
import com.example.techtree.global.security.SecurityUser;
import com.example.techtree.global.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
//@Transactional(readOnly = true)
@Service
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member MemberCreate(String loginId, String username, String password, String email, LocalDate birthday,
                               String phoneNumber, String profile) {
        validateInput(username, password, email);

        Member member = new Member();
        member.setLoginId(loginId);
        member.setUsername(username);
        member.setPassword(passwordEncoder.encode(password));
        member.setEmail(email);
        member.setBirthday(birthday);
        member.setProvider(SocialProvider.APP);
        member.setRole(Role.USER);
        member.setPhoneNumber(phoneNumber);
        // member.setProfileImage(profileImage);

        String defaultProfileImage = "/images/기본 프로필.jpg";
        if (profile == null || profile.isEmpty()) {
            profile = defaultProfileImage; // 기본 이미지 파일 경로로 변경
        }
        member.setProfile(profile);

        this.memberRepository.save(member);
        return member;
    }

    private void validateInput(String username, String password, String email) {
        validateUsername(username);
        validatePassword(password);
        validateEmail(email);
    }

    private void validateUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("사용자 이름을 입력해주세요.");
        }
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }
    }

    private void validateEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("이메일을 입력해주세요.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
//        return new User(member.getLoginId(), member.getPassword(), Collections.emptyList());
        return new SecurityUser(member.getMemberId(), member.getUsername(), member.getPassword(),
                member.getProfileImage(), member.getLoginId(), member.getAuthorities());
    }

    public Member findByLoginId(String loginId) {
        System.out.println("loginId = " + loginId);
        Optional<Member> member = memberRepository.findByLoginId(loginId);
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new DataNotFoundException("user not found");
        }
    }

    public String getLoginId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = "";
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            if (userDetails instanceof SecurityUser) {
                email = ((SecurityUser) userDetails).getLoginId();
                System.out.println("이메일 가져오기 성공 email = " + email);
            } else {
                throw new DataNotFoundException("user not found");
            }
        } else {
            throw new DataNotFoundException("user not found");
        }
        return email;
    }

    public Optional<String> findLoginIdByUsernameEmailAndPhoneNumber(String username, String email, String phoneNumber) {
        return memberRepository.findByUsernameAndEmailAndPhoneNumber(username, email, phoneNumber)
                .map(Member::getLoginId);
    }

    public boolean validateUser(String loginId, String username, String email, String phoneNumber) {
        Member member = memberRepository.findByLoginIdAndUsernameAndEmailAndPhoneNumber(loginId, username, email, phoneNumber);
        return member != null;
    }

    @Transactional
    public void updatePassword(String loginId, String newPassword) {
        // 새 비밀번호를 암호화
        String encryptedPassword = passwordEncoder.encode(newPassword);

        // 회원 정보 조회
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new DataNotFoundException("User not found with loginId: " + loginId));

        // 비밀번호 업데이트
        member.setPassword(encryptedPassword);
    }
}
