package com.example.techtree.domain.member.dto;

import com.example.techtree.domain.member.entity.SocialProvider;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import static jakarta.persistence.EnumType.STRING;

@Data
@AllArgsConstructor
@Builder
public class KakaoUserInfo {
    private Long id;
    private String nickname;
    private String profileImg;
    private String email;
    @Enumerated(STRING)
    private SocialProvider provider;

    public KakaoUserInfo(Long id, String nickname, String profileImg, SocialProvider provider, String email) {
        this.id = id;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.provider = provider;
        this.email = email;
    }
}
