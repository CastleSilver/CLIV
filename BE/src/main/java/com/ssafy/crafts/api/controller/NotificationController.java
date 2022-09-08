package com.ssafy.crafts.api.controller;

import com.ssafy.crafts.api.response.ClassRoomResponse;
import com.ssafy.crafts.api.response.NotiResponse;
import com.ssafy.crafts.api.service.AuthService;
import com.ssafy.crafts.api.service.NotificationService;
import com.ssafy.crafts.common.util.JwtHeaderUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @FileName : NotificationController
 * @작성자 : 허성은
 * @Class 설명 : SSE 기반 알림 요청을 수행하는 Controller
 */
@RestController
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final AuthService authService;
    @GetMapping(value = "/sub", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ApiOperation(value = "알림 구독", notes = "알림을 구독한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "접근 권한 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<SseEmitter> subscribe(HttpServletResponse response, @RequestParam String authId) {
        /**
         * @Method Name : subscribe
         * @작성자 : 허성은
         * @Method 설명 : 로그인과 동시에 클라이언트로부터 요청받는 SSE 구독을 처리한다.
         *               로그인한 회원은 이벤트가 발생했을때 실시간 알림을 받을 수 있다.
         *               이전에 받지 못한 이벤트가 존재하는 경우 "Last-Event-ID"를 통해 마지막 이벤트 아이디를 받을 수 있으며, 필수 값은 아니다.
         */
        log.info("구독 신청");
        response.setContentType("text/event-stream");	// Header에 Content Type을 Event Stream으로 설정
        response.setCharacterEncoding("UTF-8");// Header에 encoding을 UTF-8로 설정

        return new ResponseEntity<>(notificationService.subscribe(authId), HttpStatus.OK);
    }

    @GetMapping(value = "/noti")
    @ApiOperation(value = "알림 전체 조회", notes = "알림을 전체 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 417, message = "조회 오류"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public List<NotiResponse> findAllNotifications(HttpServletRequest request) {
        /**
         * @Method Name : findAllNotifications
         * @작성자 : 허성은
         * @Method 설명 : 알림창을 눌러 전체 알림을 조회한다.
         */
        String token = JwtHeaderUtil.getAccessToken(request);
        String authId = authService.getAuthId(token);
        return notificationService.findAllNotifications(authId);
    }

    @PostMapping("/noti/{notificationId}")
    @ApiOperation(value = "선택 알림 조회", notes = "선택한 알림을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "조회 오류"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public void readNotification(HttpServletRequest request, @PathVariable int notificationId) {
        /**
         * @Method Name : readNotification
         * @작성자 : 허성은
         * @Method 설명 : 알림 한 개를 선택하면 읽음 처리한다.
         */
        String token = JwtHeaderUtil.getAccessToken(request);
        String authId = authService.getAuthId(token);
        notificationService.readNotification(notificationId, authId);
    }
}