package com.ssafy.crafts.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @FileName : ClassRoomResponse
 * @작성자 : 허성은
 * @Class 설명 : 세션 관련 요청에 대한 리스폰스 바디
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassRoomResponse {
    String hostId;
    String token;
}
