package com.example.techtree.domain.Investment.controller;

import com.example.techtree.domain.Investment.model.OauthInfo;
import com.example.techtree.domain.Investment.model.TokenInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Component
public class AccessTokenManager {
    private final WebClient webClient;
    public static String ACCESS_TOKEN;
    public static long last_auth_time = 0;

    public AccessTokenManager(WebClient.Builder
                              webClientBuilder) {
        this.webClient =
                webClientBuilder.baseUrl(KisConfig.REST_BASE_URL).build();
    }

    /*@PostConstruct
    private void TokenInit() {
        // 초기화 처리
        generateAccessToken();
    }*/



    public String getAccessToken() {
        //LocalDateTime
        System.out.println("ACCESS_TOKEN = " + ACCESS_TOKEN);
        // 현재 시간(초)
        long currentTimeInSeconds = Instant.now().getEpochSecond();
        // 오늘로부터 86000초 뒤의 시간(초)
        long futureTimeInSeconds = currentTimeInSeconds + 86000;
        System.out.println("futureTimeInSeconds = " + futureTimeInSeconds);
        if (ACCESS_TOKEN == null) {
            ACCESS_TOKEN = generateAccessToken();
            System.out.println("generate ACCESS_TOKEN: " + ACCESS_TOKEN);
        }
        System.out.println("generate ACCESS_TOKEN: " + ACCESS_TOKEN + "\n" + last_auth_time);
        return ACCESS_TOKEN;
    }

    public String generateAccessToken() {
        String url = KisConfig.REST_BASE_URL + "/oauth2/tokenP";
        OauthInfo bodyOauthInfo = new OauthInfo();
        bodyOauthInfo.setGrant_type("client_credentials");
        bodyOauthInfo.setAppkey(KisConfig.APPKEY);
        bodyOauthInfo.setAppsecret(KisConfig.APPSECRET);

        Mono<TokenInfo> mono = webClient.post()
                .uri(url)
                .header("content-type", "application/json")
                .bodyValue(bodyOauthInfo)
                .retrieve()
                .bodyToMono(TokenInfo.class);

        TokenInfo tokenInfo = mono.block();
        System.out.println("tokenInfo = " + tokenInfo.getAccess_token() + tokenInfo.getToken_type()
                + tokenInfo.getExpires_in());
        if (tokenInfo == null) {
            throw new RuntimeException("액세스 토큰을 가져올 수 없습니다.");
        }
        ACCESS_TOKEN = tokenInfo.getAccess_token();
        last_auth_time = tokenInfo.getExpires_in();

        return ACCESS_TOKEN;
    }
}
