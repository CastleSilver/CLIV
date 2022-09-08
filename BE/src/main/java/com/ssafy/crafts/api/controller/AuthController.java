package com.ssafy.crafts.api.controller;

import com.ssafy.crafts.api.request.AuthRequest;
import com.ssafy.crafts.api.response.AuthResponse;
import com.ssafy.crafts.api.service.AuthService;
import com.ssafy.crafts.common.model.ApiResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @FileName : AuthController
 * @작성자 : 허성은
 * @Class 설명 : 카카오 로그인 관련 요청을 수행하는 Controller
*/
@RestController
@Slf4j
@RequestMapping("/kakao")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService kakaoService;
    @ApiOperation(value = "카카오 로그인", notes = "카카오 엑세스 토큰을 이용하여 사용자 정보 받아 저장하고 앱의 토큰 전환")
    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> kakaoAuthRequest(@RequestBody AuthRequest authRequest) {
        log.info("로그인 요청");
        log.debug("로그인 요청");
        System.out.println("로그인");
        return ApiResponse.success(kakaoService.login(authRequest));
    }
}
