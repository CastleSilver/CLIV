package com.ssafy.crafts.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @FileName : ClassJoinResponse
 * @작성자 : 허성은
 * @Class 설명 : 수업 신청 API 요청에 대한 리스폰스 바디 정의
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassJoinResponse {
    int memberCnt;
}
