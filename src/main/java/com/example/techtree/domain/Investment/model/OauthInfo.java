package com.example.techtree.domain.Investment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OauthInfo {
    private String grant_type;
    private String appkey;
    private String appsecret;
}
