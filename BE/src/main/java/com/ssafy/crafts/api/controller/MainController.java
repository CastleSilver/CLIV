package com.ssafy.crafts.api.controller;

import com.ssafy.crafts.api.response.MainResponse;
import com.ssafy.crafts.api.service.AuthService;
import com.ssafy.crafts.api.service.MainServiceImpl;
import com.ssafy.crafts.common.util.JwtHeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * @FileName : MainController
 * @작성자 : 허성은
 * @Class 설명 : 메인 화면을 담당하는 Controller
 */
@Api(value = "수업 관련 API", tags = {"ClassController"}, description = "수업 관련 컨트롤러")
@RestController
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainController {
    private final MainServiceImpl mainServiceImpl;
    private final AuthService authService;

    @ApiOperation(value = "메인화면 진입 시 수업 리스트 조회", notes = "메인화면 진입 시 수업리스트를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 204, message = "조회할 데이터가 없음"),
            @ApiResponse(code = 500, message = "서버 에러 발생")
    })
    @GetMapping("/list")
    public ResponseEntity<MainResponse> findAllVideo(HttpServletRequest request) {
        /**
         * @Method Name : findAllVideo
         * @작성자 : 허성은
         * @Method 설명 : 메인화면 수업 리스트를 조회한다.
         */
        String token = JwtHeaderUtil.getAccessToken(request);
        MainResponse mainResponse = mainServiceImpl.findAllClassList(authService.getAuthId(token));
        if(mainResponse == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(mainResponse, HttpStatus.OK);
    }


}
