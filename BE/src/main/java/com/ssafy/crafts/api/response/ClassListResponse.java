package com.ssafy.crafts.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @FileName : ClassListResponse
 * @작성자 : 허성은
 * @Class 설명 : 클래스 화면에서 수업 조회 요청에 대한 리스폰스 바디
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassListResponse {
    List<ClassInfoResponse> classList;
}
