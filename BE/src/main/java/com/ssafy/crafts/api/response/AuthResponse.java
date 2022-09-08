package com.ssafy.crafts.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
* 카카오 로그인 API ([POST] /kakao-login) 요청에 대한 응답값 정의.
*/
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private String appToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;
}
