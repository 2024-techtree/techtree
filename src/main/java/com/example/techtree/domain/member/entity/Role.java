package com.example.techtree.domain.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor // private 필드로 생성자 구성
public enum Role {
    GUEST("ROLE_GUEST", "Guest"),
    USER("ROLE_USER", "Common User"),
    ADMIN("ROLE_ADMIN", "Admin");

    private final String key;
    private final String title;
}