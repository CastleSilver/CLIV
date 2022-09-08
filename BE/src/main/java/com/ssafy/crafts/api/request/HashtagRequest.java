package com.ssafy.crafts.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @FileName : HashtagRequest
 * @작성자 : 허성은
 * @Class 설명 : 수업에 등록하는 해시태그 설정 관련 API 요청에 필요한 리퀘스트 바디 정의
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HashtagRequest {
    int hashtagId;
}
