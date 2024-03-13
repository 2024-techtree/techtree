package com.example.techtree.domain.member.service;

import com.example.techtree.domain.member.dao.MemberRepository;
import com.example.techtree.domain.member.dto.KakaoUserInfo;
import com.example.techtree.domain.member.entity.Member;
import com.example.techtree.domain.member.entity.SocialProvider;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class KakaoService {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    public String getToken(String code) throws IOException {
        // 인가코드로 토큰받기
        String host = "https://kauth.kakao.com/oauth/token";
        URL url = new URL(host);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String token = "";
        try {
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true); // 데이터 기록 알려주기

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=bcda04f3dbb1052307427b9508ca0914");
            sb.append("&redirect_uri=http://localhost:8080/kakao/callback");
            sb.append("&code=" + code);

            bw.write(sb.toString());
            bw.flush();

            int responseCode = urlConnection.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("result = " + result);

            // json parsing
            JSONParser parser = new JSONParser();
            JSONObject elem = (JSONObject) parser.parse(result);

            String access_token = elem.get("access_token").toString();
            String refresh_token = elem.get("refresh_token").toString();
            System.out.println("refresh_token = " + refresh_token);
            System.out.println("access_token = " + access_token);

            token = access_token;

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return token;
    }


    public KakaoUserInfo getUserInfo(String access_token) {
        String host = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(host);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Bearer " + access_token);
            urlConnection.setRequestMethod("GET");

            int responseCode = urlConnection.getResponseCode();
            System.out.println("responseCode = " + responseCode);


            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            String res = "";
            while ((line = br.readLine()) != null) {
                res += line;
            }

            System.out.println("res = " + res);



            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(res);
            JSONObject properties = (JSONObject) obj.get("properties");
            JSONObject kakaoAccount = (JSONObject) obj.get("kakao_account");
            System.out.println("res = " + res);
            String id = obj.get("id").toString();
            String nickname = properties.get("nickname").toString();
            Object profileImgObject = properties.get("profile_image");
            String profileImg = (profileImgObject != null) ? profileImgObject.toString() : "https://kr.object.ncloudstorage.com/trend-hub-bucket/images/logo.png";  // 프로필 이미지 동의 안할 시 null 값 대신 기본 logo.png로 대체
            System.out.println("profileImg = " + profileImg);

            String email = kakaoAccount.get("email").toString();
            System.out.println("email = " + email);
            System.out.println("properties = " + properties);

            return KakaoUserInfo.builder()
                    .id(Long.valueOf(id))
                    .email(email)
                    .nickname(nickname)
                    .profileImg(profileImg)
                    .provider(SocialProvider.KAKAO)
                    .build();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("카카오 정보를 가져오는데 실패했습니다!");
        }
    }

    public String getAgreementInfo(String access_token) {
        String result = "";
        String host = "https://kapi.kakao.com/v2/user/scopes";
        try {
            URL url = new URL(host);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Authorization", "Bearer " + access_token);

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }

            int responseCode = urlConnection.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            // result is json format
            br.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Member login(KakaoUserInfo userInfo) {
        System.out.println(userInfo);

        Optional<Member> opUser = memberService.findByProviderAndProviderId(SocialProvider.valueOf("KAKAO"), userInfo.getId().toString());

        System.out.println("opUser 통과");

        if (opUser.isPresent()) {
            return opUser.get();
        }

        //랜덤 닉네임 생성
        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;
        while (memberRepository.existsByNickname("user_" + randomNumber)) {
            randomNumber = random.nextInt(9000) + 1000;
        }
        String randomNickname = "user_" + randomNumber;

        // 강제 회원가입
        Member member = Member.builder()
                .login_id(userInfo.getEmail())
                .profileImage(userInfo.getProfileImg())
                .username(userInfo.getNickname())
                .build();

        return memberRepository.save(member);
    }
}