package com.ssafy.crafts.api.controller;

import com.ssafy.crafts.api.request.ChatRoomRequest;
import com.ssafy.crafts.api.response.ChatRoomResponse;
import com.ssafy.crafts.api.service.ChatService;
import com.ssafy.crafts.db.entity.ChatRoom;
import io.swagger.annotations.*;
import io.swagger.models.Model;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @FileName : ChatRoomController
 * @작성자 : 김민주
 * @Class 설명 : 채팅 관련 CRUD를 담당하는 Controller
 */
@Api(value = "채팅 관련 API", tags = {"ChatRoomController"}, description = "채팅 관련 컨트롤러")
@RestController
@Slf4j
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatService chatService;

    /**
     * @Method Name : createRoom
     * @작성자 : 김민주
     * @Method 설명 : 채팅방 생성
     */
    @PostMapping("/room")
    @ResponseBody
    public ResponseEntity<Object> createRoom(@PathVariable ChatRoomRequest chatRoomRequest) {

        if(chatService.getChatRoomByMtId(chatRoomRequest.getMtId()) != null){

            chatService.createRoom(chatRoomRequest);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {     // mtId로 이미 채팅방이 만들어진 경우
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /**
     * @Method Name : findAllRoom
     * @작성자 : 김민주
     * @Method 설명 : 모든 채팅방 목록 반환
     */
    @GetMapping("/rooms/{authId}")
    @ResponseBody
    public List<ChatRoomResponse> room(@PathVariable String authId) {
        log.info("# All Chat Rooms");
        return chatService.findAllRoom(authId);
    }

    /**
     * @Method Name : roomInfo
     * @작성자 : 김민주
     * @Method 설명 : 특정 채팅방 조회
     */
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ResponseEntity<?> roomInfo(@PathVariable int roomId) {

        ChatRoomResponse chatRoomResponse = chatService.findRoomById(roomId);
        if(chatRoomResponse != null){
            return new ResponseEntity<>(chatRoomResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
