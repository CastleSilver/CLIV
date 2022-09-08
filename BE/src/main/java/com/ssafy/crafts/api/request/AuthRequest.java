package com.ssafy.crafts.api.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
* 카카오 로그인 API ([POST] /kakao-login) 요청에 필요한 리퀘스트 바디 정의.
*/
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    private String accessToken;
}