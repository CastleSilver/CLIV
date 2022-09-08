package com.ssafy.crafts.api.controller;

import com.ssafy.crafts.api.request.MatchingRequest;
import com.ssafy.crafts.api.response.CategoryResponse;
import com.ssafy.crafts.api.response.MBoardTeacherResponse;
import com.ssafy.crafts.api.response.MatchingResponse;
import com.ssafy.crafts.api.service.AuthService;
import com.ssafy.crafts.api.service.MatchingService;
import com.ssafy.crafts.common.util.JwtHeaderUtil;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @FileName : MatchingController
 * @작성자 : 김민주
 * @Class 설명 : 매칭 관련 CRUD를 담당하는 Controller
 */
@Api(value = "매칭 관련 API", tags = {"MatchingController"}, description = "매칭 관련 컨트롤러")
@RestController
@Slf4j
@Transactional
@RequestMapping("/matching")
@RequiredArgsConstructor
public class MatchingController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    private final MatchingService matchingService;
    private final AuthService authService;

    @PostMapping
    @ApiOperation(value = "새로운 매칭 요청 정보를 등록한다. DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 404, message = "등록 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<Object> createMatching(HttpServletRequest request,
                                                 @RequestBody MatchingRequest matchingRequest){
        /**
         * @Method Name : createMatching
         * @작성자 : 김민주
         * @Method 설명 : 새로운 매칭 요청글 정보를 등록한다.
         */
        log.info("매칭 정보 등록 시작");
        log.info("토큰 얻어오기");
        String token = JwtHeaderUtil.getAccessToken(request);
        log.info("토큰에서 아이디 정보 얻어 회원 아이디로 할당");
        matchingRequest.setAuthId(authService.getAuthId(token));
//        matchingRequest.setAuthId("1");     // test용


        log.info("매칭글 정보 등록");
        int mBoardId = matchingService.createMBoard(matchingRequest);

        log.info("매칭 조건에 일치하는 선생님에게 요청글 등록");
        matchingService.matching(mBoardId, matchingRequest.getCategoryId(), matchingRequest.getTeacherGender());

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping("tboard/list/{teacherId}")
    @ApiOperation(value = "강사 id로 해당 강사가 요청받은 매칭정보 목록 조회")
    public  ResponseEntity<?> getMBoardTeacherListByTeacherId(@PathVariable String teacherId){
        /**
          * @Method Name : getMBoardTeacherListByTeacherId
          * @작성자 : 김민주
          * @Method 설명 : 강사 id로 강사가 요청 받은 매칭글 정보 목록을 조회한다.
          */
        log.info("매칭 리스트 조회 시작");
        List<MBoardTeacherResponse> list = matchingService.getMBoardTeacherListByTeacherId(teacherId);
        log.info("return 반환");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/board/list/{authId}")
    @ApiOperation(value = "회원 id로 회원이 작성한 매칭글 조회")
    public  ResponseEntity<?> getMBoardListByAuthId(@PathVariable String authId){
        /**
         * @Method Name : getMBoardListByAuthId
         * @작성자 : 김민주
         * @Method 설명 : 회원 id로 매칭 요청글 리스트를 조회한다.
         */
        log.info("매칭 리스트 조회 시작");
        List<MatchingResponse> mBoardList = matchingService.findMBoardListByAuthId(authId);
        log.info("return 반환");
        return new ResponseEntity<>(mBoardList, HttpStatus.OK);
    }


    @PostMapping("/agree/{mtId}")
    @ApiOperation(value = "매칭보드_선생님 id로 수강생의 클래스 개설을 동의로 변경")
    public ResponseEntity<String> agreeMatching(@PathVariable int mtId){
        /**
         * @Method Name : agreeMatching
         * @작성자 : 김민주
         * @Method 설명 : 수강생이 클래스 개설을 동의한다.
         */
        log.info("해당 매칭글이 존재하는지 판단 시작");
        if(matchingService.getMBoardTeacherById(mtId) != null){
            log.info("동의여부 업데이트");
            matchingService.updateAgreeYnById(mtId);
            log.info("return 반환");
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/category")
    @ApiOperation(value = "카테고리 ID와 이름을 조회")
    public ResponseEntity<?> getCategoryList() {

        List<CategoryResponse> list = matchingService.getCategoryList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
