package com.ssafy.crafts.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @FileName : PhraseReviewRequest
 * @작성자 : 김민주
 * @Class 설명 : 선택된 리뷰문구 관련 API 요청에 필요한 리퀘스트 바디 정의
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("PhraseReviewRequest")
@Builder
public class PhraseReviewRequest {
    @ApiModelProperty(name = "prId", example = "리뷰문구 id")
    int prId;   // 리뷰문구 id
}
