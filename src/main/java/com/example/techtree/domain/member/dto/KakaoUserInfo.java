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
}
