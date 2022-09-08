package com.ssafy.crafts.api.controller;

import com.ssafy.crafts.api.response.FileResponse;
import com.ssafy.crafts.api.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @FileName : CKController
 * @작성자 : 허성은
 * @Class 설명 : 수업 설명의 이미지 저장 요청를 담당하는 Controller
 */
@Api(value = "이미지 저장 관련 API", tags = {"CKController"}, description = "이미지 저장 컨트롤러")
@RestController
@Slf4j
@RequestMapping("/image")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CKController {

    private final FileUploadService fileUploadService;


    @PostMapping("/upload")
    @ApiOperation(value = "이미지 url 생성 후 반환", notes = "CK5 에디터에 올라온 이미지의 url을 생성후 반환한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 404, message = "등록 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<FileResponse> fileUploadFromCKEditor(@RequestPart(value = "upload", required = false) MultipartFile image) throws IOException {

        /**
         * @Method Name : fileUploadFromCKEditor
         * @작성자 : 허성은
         * @Method 설명 : 이미지를 받아 s3에 저장 후 url을 json 형태로 반환.
         */
        return new ResponseEntity<>(FileResponse.builder().
                uploaded(true).
                url(fileUploadService.upload(image)).
                build(), HttpStatus.OK);
    }
}
