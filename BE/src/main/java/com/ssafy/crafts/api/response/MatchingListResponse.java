package com.ssafy.crafts.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * @FileName : MatchingListInfoResponse
 * @작성자 : 허성은
 * @Class 설명 : 매칭 리스트 정보 조회 API 요청에 대한 리스폰스 바디 정의
 */
@Getter
@ToString
@Builder
public class MatchingListResponse {
    List<MatchingResponse> matchingList;
}
