package com.ssafy.crafts.api.controller;

import com.ssafy.crafts.api.request.ChatMessageRequest;
import com.ssafy.crafts.api.response.ChatMessageResponse;
import com.ssafy.crafts.api.service.AuthService;
import com.ssafy.crafts.api.service.ChatService;
import com.ssafy.crafts.common.util.JwtHeaderUtil;
import com.ssafy.crafts.db.entity.ChatMessage;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @FileName : ChatRoomController
 * @작성자 : 김민주
 * @Class 설명 : 메시지 전달과 관련된 Controller
 */
@Api(value = "채팅 메시지 전달 관련 API", tags = {"MessageController"}, description = "채팅메시지 관련 컨트롤러")
@RestController
@Slf4j
@RequestMapping
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations sendingOperations;
    private final ChatService chatService;
    private final AuthService authService;

//    @MessageMapping("/chat/message")
//    public void enter(ChatMessageRequest message) {
//        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
//            message.setMessage(message.getSender()+"님이 입장하였습니다.");
//        }
//        sendingOperations.convertAndSend("/queue/chat/room/"+message.getChatRoomId(), message);
//    }

    /**
     * /sub/room/12345 - 구독(roomId: 12345)
     * /pub/message  - 메시지 발행
     */
    @MessageMapping("/message")     // 클라이언트에서 /pub/message 로 메시지를 발행한다.
    public void message(HttpServletRequest request, ChatMessageRequest message) {

        String token = JwtHeaderUtil.getAccessToken(request);
        message.setSenderId(authService.getAuthId(token));

        log.info("채팅 저장");
        chatService.saveMessage(message);

        // 메시지에 정의된 채널 id에 메시지를 보낸다.
        // /sub/room/방아이디 에 구독중인 쿨라이언트에게 메시지를 보낸다.
        sendingOperations.convertAndSend("/sub/room/"+message.getChatRoomId(), message);
    }

//    @MessageMapping("/{roomId}")    // 여기로 전송되면 메서드 호출 -> WebSocketConfig prefixes에서 적용한건 앞에 생략
//    @SendTo("/sub/room/{roomId}")   // 구독하고 있는 장소로 메시지 전송 (목적지)
//    public ChatMessageResponse test(@DestinationVariable int croomId, ChatMessageResponse message) {
//
//        // 채팅 저장
//        ChatMessage chatMessage = chatService.createChatMessage(croomId, message.getSender(), message.getMessage());
//        return ChatMessageResponse.builder()
//                .roomId(croomId)
//                .sender(chatMessage.getSender())
//                .message(chatMessage.getMessage())
//                .build();
//    }
}
