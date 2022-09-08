package com.ssafy.crafts.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * @FileName : CategoryResponse
 * @작성자 : 김민주
 * @Class 설명 : 카테고리 정보 조회 API 요청에 대한 리스폰스 바디 정의
 */
@Getter
@ToString
@Builder
public class CategoryResponse {

    private int id;     // 카테고리 id
    private String content;     // 카테고리명

}
