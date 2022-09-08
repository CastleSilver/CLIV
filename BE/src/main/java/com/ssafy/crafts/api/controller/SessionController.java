package com.ssafy.crafts.api.controller;

import com.ssafy.crafts.api.response.ClassRoomResponse;
import com.ssafy.crafts.api.service.AuthService;
import com.ssafy.crafts.api.service.ClassRoomService;
import com.ssafy.crafts.common.util.JwtHeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @FileName : SessionController
 * @작성자 : 허성은
 * @Class 설명 : 화상 관련 API 호출을 담당하는 Controller
 */
@Api(value = "화상 관련 API", tags = {"SessionController"}, description = "화상 관련 컨트롤러")
@RestController
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/class")
@RequiredArgsConstructor
public class SessionController {

    private final ClassRoomService classRoomService;
    private final AuthService authService;

    @PostMapping(value="/session/{classId}")
    @ApiOperation(value = "세션 생성", notes = "수업 세션을 생성한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "생성 성공"),
            @ApiResponse(code = 401, message = "실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<ClassRoomResponse> createClassRoom(HttpServletRequest request, @PathVariable int classId) {
        /**
         * @Method Name : createClassRoom
         * @작성자 : 허성은
         * @Method 설명 : 수업 세션을 생성하고 토큰을 반환한다.
         */
        String token = JwtHeaderUtil.getAccessToken(request);
        String authId = authService.getAuthId(token);
        if (!classRoomService.checkValidation(authId, classId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            ClassRoomResponse classRoomResponse = classRoomService.createClassRoom(authId, classId);
            return new ResponseEntity<>(classRoomResponse, HttpStatus.CREATED);
        }
    }

    @PatchMapping(value="/session/{classId}")
    @ApiOperation(value = "세션 종료", notes = "수업 세션을 종료한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "종료 성공"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> closeClassRoom(HttpServletRequest request, @PathVariable int classId) {
        /**
         * @Method Name : closeClassRoom
         * @작성자 : 허성은
         * @Method 설명 : 선생님일 경우만 수업 세션을 종료한다.
         */
        String token = JwtHeaderUtil.getAccessToken(request);
        String authId = authService.getAuthId(token);
        if (!classRoomService.closeSession(classId, authId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
