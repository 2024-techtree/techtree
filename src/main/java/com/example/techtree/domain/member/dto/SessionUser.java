package com.example.techtree.domain.member.dto;

import com.example.techtree.domain.member.entity.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(Member member) {
        this.name = member.getUsername();
        this.email = member.getEmail();
        this.picture = member.getProfileImage();
    }
}
