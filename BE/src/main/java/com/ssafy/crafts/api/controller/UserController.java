package com.ssafy.crafts.api.controller;

import com.ssafy.crafts.api.service.AuthService;
import com.ssafy.crafts.api.service.UserService;
import com.ssafy.crafts.common.util.JwtHeaderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @FileName : UserController
 * @작성자 : 허성은
 * @Class 설명 : 마이페이지 관련 기능을 담당하는 Controller
 */
@RestController
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final AuthService authService;
    private final UserService userService;

    @PutMapping("/change/role")
    public ResponseEntity<Object> changeRoleToTeacher(HttpServletRequest request) {
        /**
         * @Method Name : changeRoleToTeacher
         * @작성자 : 허성은
         * @Method 설명 : 선생님으로 RoleType을 변경한다.
         */
        String token = JwtHeaderUtil.getAccessToken(request);
        userService.changeRoleType(authService.getAuthId(token));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
