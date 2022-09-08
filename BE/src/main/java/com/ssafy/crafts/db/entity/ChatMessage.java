package com.ssafy.crafts.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
@Table(name = "CHAT_MESSAGE")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private int id;     // 메시지 ID (PK)

    @Column(name = "sender", nullable = false)
    private String senderId;      // 보내는 사람

    @Column(name = "receiverId", nullable = false)
    private String receiverId;      // 받는 사람

    @Column(name="message", nullable = false, length = 200)
    private String message;     // 메시지 내용

    @Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp regDate;      // 생성날짜

//    @Column(name = "type")
//    private String type;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "message_type")
//    private MessageType type;

    // N:1 관계 : 채팅방메시지 - 채팅방
    @ManyToOne
    @JoinColumn(name = "croom_id")
    private ChatRoom chatRoom;

    @Builder
    public ChatMessage(int id, String senderId, String receiverId, String message, Timestamp regDate, ChatRoom chatRoom) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.regDate = regDate;
        this.message = message;
        this.regDate = regDate;
        this.chatRoom = chatRoom;
    }

    /**
     * @Method Name : createRoom
     * @Method 설명 : 채팅방 생성
     */
    public ChatMessage createMessage(ChatRoom chatRoom, String senderId, String receiverId, String message){
        return ChatMessage.builder()
                .chatRoom(chatRoom)
                .senderId(senderId)
                .receiverId(receiverId)
                .message(message)
                .build();
    }

    public void setSender(String senderId) {
        this.senderId = senderId;
    }

//    public void newConnect(){
//        this.type = "new";
//    }
//
//    public void closeConnect() {
//        this.type = "close";
//    }

}