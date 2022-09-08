package com.ssafy.crafts.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * @FileName : MatchingInfoResponse
 * @작성자 : 김민주
 * @Class 설명 : 매칭 정보 조회 API 요청에 대한 리스폰스 바디 정의
 */
@Getter
@ToString
@Builder
public class ChatMessageResponse {
    @ApiModelProperty(name = "roomId")
    private int roomId;     // 채팅방 id

    @ApiModelProperty(name = "sender")
    private String sender;      // 보내는 사람

    @ApiModelProperty(name = "message")
    private String message;     // 메시지 내용

//    @ApiModelProperty(name = "regDate")
//    private Timestamp regDate;      // 생성일
}
