package com.ssafy.crafts.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.sql.Timestamp;

/**
 * @FileName : ChatMessageRequest
 * @작성자 : 김민주
 * @Class 설명 : 채팅 관련 API 요청에 필요한 리퀘스트 바디 정의
 */
@Getter
//@Setter
@AllArgsConstructor
@ApiModel("ChatMessageRequest")
@Builder
public class ChatRoomRequest {

    @ApiModelProperty(name = "roomId", example = "id", hidden = true)
    private int roomId;      // 채팅방 id

    @ApiModelProperty(name = "teacherId", example = "보내는 사람")
    private String roomName;    // 채팅방 이름

    @ApiModelProperty(name = "teacherId", example = "보내는 사람", hidden = true)
    private Timestamp regDate;      // 생성일

    @ApiModelProperty(name = "mtId", example = "선생님_매칭보드 id")
    private int mtId;   // 선생님_매칭보드 id


}
