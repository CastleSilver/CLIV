package com.ssafy.crafts.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @FileName : MainResponse
 * @작성자 : 허성은
 * @Class 설명 : 메인 화면 수업 리스트 조회에 대한 리스폰스 바디 정의
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MainResponse {
    List<ClassInfoResponse> madeCL;
    List<ClassInfoResponse> appliedCL;
//    List<ClassInfoResponse> categoryCL;
    List<ClassInfoResponse> classTimeCL;
    List<ClassInfoResponse> headcountCL;
    List<ClassInfoResponse> all;
}
