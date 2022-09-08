package com.ssafy.crafts.api.service;

import com.ssafy.crafts.api.request.ChatMessageRequest;
import com.ssafy.crafts.api.request.ChatRoomRequest;
import com.ssafy.crafts.api.response.ChatRoomResponse;
import com.ssafy.crafts.db.entity.ChatMessage;
import com.ssafy.crafts.db.entity.ChatRoom;
import com.ssafy.crafts.db.repository.jpaRepo.ChatMessageRepository;
import com.ssafy.crafts.db.repository.jpaRepo.ChatRoomRepository;
import com.ssafy.crafts.db.repository.jpaRepo.MBoardTeacherRepository;
import com.ssafy.crafts.db.repository.querydslRepo.ChatRoomQuerydslRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @FileName : ChatServiceImpl
 * @작성자 : 김민주
 * @Class 설명 : 채팅 기능 관련 비즈니스 처리 로직을 위한 서비스 구현 정의
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

//    private Map<String, ChatRoom> chatRooms;
    private final ChatRoomRepository chatRoomRepository;;
    private final ChatRoomQuerydslRepository chatRoomQuerydslRepository;
    private final MBoardTeacherRepository mBoardTeacherRepository;
    private final ChatMessageRepository chatMessageRepository;


    // authId로 채팅방 목록 불러오기
    public List<ChatRoomResponse> findAllRoom(String authId) {
//        //채팅방 최근 생성 순으로 반환
//        List<ChatRoom> result = new ArrayList<>(chatRooms.values());
//        Collections.reverse(result);
//
//        return result;
        List<ChatRoom> chatRoomList = chatRoomQuerydslRepository.findAllRoomByAuthId(authId);
        List<ChatRoomResponse> list = new ArrayList<>();

        for (ChatRoom chatRoom : chatRoomList){
            list.add(ChatRoomResponse.builder()
                    .id(chatRoom.getCroomId())
                    .regDate(chatRoom.getRegDate())
                    .roomName(chatRoom.getRoomName())
                    .build());
        }
        return list;

    }

    // 특정 채팅방 하나 불러오기
    public ChatRoomResponse findRoomById(int roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).get();

        return ChatRoomResponse.builder()
                .id(chatRoom.getCroomId())
                .regDate(chatRoom.getRegDate())
                .roomName(chatRoom.getRoomName())
                .build();
    }

    //채팅방 생성
    public void createRoom(ChatRoomRequest chatRoomRequest) {

        ChatRoom chatRoom = ChatRoom.builder()
                .roomName(chatRoomRequest.getRoomName())
                .regDate(chatRoomRequest.getRegDate())
                .mBoardTeacher(mBoardTeacherRepository.findById(chatRoomRequest.getMtId()).get())
                .build();

        chatRoomRepository.save(chatRoom);
    }

    // 채팅 저장
    public void saveMessage(ChatMessageRequest message){

        ChatMessage chatMessage = ChatMessage.builder()
                .senderId(message.getSenderId())
                .receiverId(message.getReceiverId())
                .message(message.getMessage())
                .regDate(message.getRegDate())
                .chatRoom(chatRoomRepository.findById(message.getChatRoomId()).get())
                .build();

        chatMessageRepository.save(chatMessage);
    }

    // mtId를 가진 채팅방 조회
    public ChatRoomResponse getChatRoomByMtId(int mtId){
        ChatRoom chatRoom = chatRoomQuerydslRepository.findByMtId(mtId);

        return ChatRoomResponse.builder()
                .id(chatRoom.getCroomId())
                .regDate(chatRoom.getRegDate())
                .roomName(chatRoom.getRoomName())
                .build();
    }

}
