package com.ssafy.crafts.api.response;

import com.ssafy.crafts.db.entity.ChatRoom;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import java.sql.Timestamp;

/**
 * @FileName : MatchingInfoResponse
 * @작성자 : 김민주
 * @Class 설명 : 매칭 정보 조회 API 요청에 대한 리스폰스 바디 정의
 */
@Getter
@ToString
@Builder
public class ChatRoomResponse {
    @ApiModelProperty(name = "roomId")
    private int id;     // PK

    @ApiModelProperty(name = "regDate")
    private Timestamp regDate;      // 생성일

    @ApiModelProperty(name = "roomName")
    private String roomName;    // 채팅방 이름

}
