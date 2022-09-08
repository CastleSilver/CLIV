package com.ssafy.crafts.api.controller;

import com.ssafy.crafts.api.request.ClassInfoRequest;
import com.ssafy.crafts.api.response.ClassInfoResponse;
import com.ssafy.crafts.api.response.ClassJoinResponse;
import com.ssafy.crafts.api.response.ClassListResponse;
import com.ssafy.crafts.api.service.AuthService;
import com.ssafy.crafts.api.service.ClassService;
import com.ssafy.crafts.common.util.JwtHeaderUtil;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * @FileName : ClassController
 * @작성자 : 허성은
 * @Class 설명 : 수업 관련 CRUD를 담당하는 Controller
 */
@Api(value = "수업 관련 API", tags = {"ClassController"}, description = "수업 관련 컨트롤러")
@RestController
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/class")
@RequiredArgsConstructor
public class ClassController {
    private final ClassService classService;
    private final AuthService authService;

    @PostMapping(
            value="/create",
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ApiOperation(value = "수업 정보 등록", notes = "새로운 수업 정보를 등록한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 404, message = "등록 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<Object> insertClassInfo(HttpServletRequest request,
                                                  @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail,
                                                  @RequestPart(value = "classInfoRequest") ClassInfoRequest classInfoRequest
    ) throws ParseException {
        /**
         * @Method Name : insertClassInfo
         * @작성자 : 허성은
         * @Method 설명 : 새로운 수업 정보를 등록한다.
         */
        log.info("수업 정보 등록 시작");
        log.info("토큰 얻어오기");
        String token = JwtHeaderUtil.getAccessToken(request);
        log.info("토큰에서 아이디 정보 얻어 선생님 아이디로 할당");
        classInfoRequest.setTeacherId(authService.getAuthId(token));
        log.info("수업 정보와 썸네일 등록");
        classService.insertClassInfo(classInfoRequest, thumbnail);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{classId}")
    @ApiOperation(value = "수업 정보 단일 조회", notes = "수업 id로 해당 수업을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 204, message = "조회할 데이터가 없음"),
            @ApiResponse(code = 500, message = "서버 에러 발생")
    })
    public ResponseEntity<ClassInfoResponse> findClassInfoByClassId(
            @PathVariable @ApiParam(value = "조회할 수업 정보의 id", required = true) int classId){
        /**
         * @Method Name : findClassInfoByClassId
         * @작성자 : 허성은
         * @Method 설명 : 수업 id로 수업 정보를 조회한다.
         */
        ClassInfoResponse classInfoResponse = classService.findClassInfoById(classId);
        if(classInfoResponse == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(classInfoResponse, HttpStatus.OK);
    }

    @PostMapping("/{classId}")
    @ApiOperation(value = "수업 신청", notes = "수업 id로 조회된 수업을 참여한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 204, message = "조회할 데이터가 없음"),
            @ApiResponse(code = 500, message = "서버 에러 발생")
    })
    public ResponseEntity<ClassJoinResponse> joinClassByClassId(HttpServletRequest request,
                                                                @PathVariable @ApiParam(value = "조회할 수업 정보의 id", required = true) int classId){
        /**
         * @Method Name : joinClassByClassId
         * @작성자 : 허성은
         * @Method 설명 : 수업 id로 조회된 수업을 참여한다.
         */
        String token = JwtHeaderUtil.getAccessToken(request);
        ClassJoinResponse classJoinResponse = classService.joinClassByMemberId(classId, authService.getAuthId(token));
        return new ResponseEntity<>(classJoinResponse, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    @ApiOperation(value = "최신 생성 수업 리스트 조회", notes = "클래스 화면에서 수업을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 204, message = "조회할 데이터가 없음"),
            @ApiResponse(code = 500, message = "서버 에러 발생")
    })
    public ResponseEntity<ClassListResponse> findClassListByRegdate(){
        /**
         * @Method Name : findClassListByRegdate
         * @작성자 : 허성은
         * @Method 설명 : 클래스 화면에서 수업을 조회한다.
         */
        ClassListResponse classListResponse = ClassListResponse.builder().classList(classService.findClassListByRegdate()).build();
        if(classListResponse == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(classListResponse, HttpStatus.OK);
    }

    @GetMapping("/manage/expected")
    @ApiOperation(value = "선생님이 예정된 수업 목록을 조회", notes = "클래스 화면에서 예정된 수업 목록을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 204, message = "조회할 데이터가 없음"),
            @ApiResponse(code = 500, message = "서버 에러 발생")
    })
    public ResponseEntity<ClassListResponse> findExpectedClassListByTeacherId(HttpServletRequest request){
        /**
         * @Method Name : findExpectedClassListByTeacherId
         * @작성자 : 허성은
         * @Method 설명 : 클래스 화면에서 예정된 수업 목록을 조회한다.
         */
        String token = JwtHeaderUtil.getAccessToken(request);
        ClassListResponse classListResponse =
                ClassListResponse.builder().classList(classService.findExpectedClassListByTeacherId(authService.getAuthId(token))).build();
        if(classListResponse == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(classListResponse, HttpStatus.OK);
    }

    @GetMapping("/manage/close")
    @ApiOperation(value = "선생님이 종료된 수업 목록을 조회", notes = "클래스 화면에서 종료된 수업 목록을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 204, message = "조회할 데이터가 없음"),
            @ApiResponse(code = 500, message = "서버 에러 발생")
    })
    public ResponseEntity<ClassListResponse> findEndedClassListByTeacherId(HttpServletRequest request){
        /**
         * @Method Name : findEndedClassListByTeacherId
         * @작성자 : 허성은
         * @Method 설명 : 클래스 화면에서 종료된 수업 목록을 조회한다.
         */
        String token = JwtHeaderUtil.getAccessToken(request);
        ClassListResponse classListResponse =
                ClassListResponse.builder().classList(classService.findEndedClassListByTeacherId(authService.getAuthId(token))).build();
        if(classListResponse == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(classListResponse, HttpStatus.OK);
    }

    @GetMapping("/join/expected")
    @ApiOperation(value = "회원이 참여 예정된 수업 목록을 조회", notes = "클래스 화면에서 예정된 수업 목록을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 204, message = "조회할 데이터가 없음"),
            @ApiResponse(code = 500, message = "서버 에러 발생")
    })
    public ResponseEntity<ClassListResponse> findExpectedClassListByMemberId(HttpServletRequest request){
        /**
         * @Method Name : findExpectedClassListByMemberId
         * @작성자 : 허성은
         * @Method 설명 : 클래스 화면에서 예정된 수업 목록을 조회한다.
         */
        String token = JwtHeaderUtil.getAccessToken(request);
        ClassListResponse classListResponse =
                ClassListResponse.builder().classList(classService.findExpectedClassListByMemberId(authService.getAuthId(token))).build();
        if(classListResponse == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(classListResponse, HttpStatus.OK);
    }

    @GetMapping("/join/close")
    @ApiOperation(value = "회원이 종료된 수업 목록을 조회", notes = "클래스 화면에서 종료된 수업 목록을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 204, message = "조회할 데이터가 없음"),
            @ApiResponse(code = 500, message = "서버 에러 발생")
    })
    public ResponseEntity<ClassListResponse> findEndedClassListByMemberId(HttpServletRequest request){
        /**
         * @Method Name : findEndedClassListByMemberId
         * @작성자 : 허성은
         * @Method 설명 : 클래스 화면에서 종료된 수업 목록을 조회한다.
         */
        String token = JwtHeaderUtil.getAccessToken(request);
        ClassListResponse classListResponse =
                ClassListResponse.builder().classList(classService.findEndedClassListByMemberId(authService.getAuthId(token))).build();
        if(classListResponse == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(classListResponse, HttpStatus.OK);
    }
}
