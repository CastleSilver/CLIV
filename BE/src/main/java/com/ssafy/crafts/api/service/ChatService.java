package com.ssafy.crafts.api.service;

import com.ssafy.crafts.api.request.ChatMessageRequest;
import com.ssafy.crafts.api.request.ChatRoomRequest;
import com.ssafy.crafts.api.request.MatchingRequest;
import com.ssafy.crafts.api.response.ChatRoomResponse;
import com.ssafy.crafts.api.response.MBoardTeacherResponse;
import com.ssafy.crafts.api.response.MatchingResponse;
import com.ssafy.crafts.db.entity.ChatMessage;
import com.ssafy.crafts.db.entity.ChatRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @FileName : ChatService
 * @작성자 : 김민주
 * @Class 설명 : 채팅 기능 관련 비즈니스 처리 로직을 위한 인터페이스 설정
 */
public interface ChatService {
    List<ChatRoomResponse> findAllRoom(String authId);
    ChatRoomResponse findRoomById(int roomId);
    void createRoom(ChatRoomRequest chatRoomRequest);
    void saveMessage(ChatMessageRequest message);
    ChatRoomResponse getChatRoomByMtId(int mtId);
}
