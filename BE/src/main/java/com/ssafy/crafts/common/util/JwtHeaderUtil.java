package com.ssafy.crafts.common.util;

import javax.servlet.http.HttpServletRequest;

public class JwtHeaderUtil {

    public final static String HEADER_AUTHORIZATION = "Authorization";
    public final static String TOKEN_PREFIX = "Bearer ";
    /*
    * Access Token 위치 : Authorization라는 이름의 헤더 값의 Bearer 이후의 문자열
    */
    public static String getAccessToken(HttpServletRequest request) {
        // Authorization 이름의 헤더값 꺼내오기
        String headerValue = request.getHeader(HEADER_AUTHORIZATION);
        // 없으면 null 반환
        if (headerValue == null) {
            return null;
        }
        // Bearer 다음값을 반환
        if (headerValue.startsWith(TOKEN_PREFIX)) {
            return headerValue.substring(TOKEN_PREFIX.length());
        }
        // Bearer로 시작안할 시 null 반환
        return null;
    }
}