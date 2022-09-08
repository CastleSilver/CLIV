package com.ssafy.crafts.api.service;

import com.ssafy.crafts.common.auth.KakaoUserResponse;
import com.ssafy.crafts.common.exception.TokenValidFailedException;
import com.ssafy.crafts.db.entity.Auth;
import com.ssafy.crafts.db.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClientKakao {

    private final WebClient webClient;

    // TODO ADMIN 유저 생성 시 getAdminUserData 메소드 생성 필요
    public Member getUserData(String accessToken) {
        KakaoUserResponse kakaoUserResponse = webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new TokenValidFailedException("Social Access Token is unauthorized")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new TokenValidFailedException("Internal Server Error")))
                .bodyToMono(KakaoUserResponse.class)
                .block();

        return Member.builder()
                .auth(Auth.builder()
                        .authId(kakaoUserResponse.getId())
                        .email(kakaoUserResponse.getKakaoAccount().getEmail())
                        .build())
                .gender(kakaoUserResponse.getKakaoAccount().getGender())
                .profileImage(kakaoUserResponse.getProperties().getProfileImage())
                .build();
    }
}