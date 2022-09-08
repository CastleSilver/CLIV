package com.ssafy.crafts.api.request;

import com.ssafy.crafts.db.entity.ChatMessage;
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
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("ChatMessageRequest")
public class ChatMessageRequest {

    @ApiModelProperty(name = "chatRoomId", example = "채팅방 id")
    private int chatRoomId;      // 채팅방 id

//    @ApiModelProperty(name = "messageType", example = "메시지 타입")
//    private ChatMessage.MessageType type;

    @ApiModelProperty(name = "senderId", example = "받는 사람")
    private String senderId;       // 받는 사람

    @ApiModelProperty(name = "receiverId", example = "보내는 사람")
    private String receiverId;       // 보내는 사람

    @ApiModelProperty(name = "message", example = "메시지 내용")
    private String message;   // 메시지 내용

    @ApiModelProperty(name = "regDate", hidden = true)
    private Timestamp regDate;      // 생성날짜

    public void setSender(String senderId) {
        this.senderId = senderId;
    }

}
