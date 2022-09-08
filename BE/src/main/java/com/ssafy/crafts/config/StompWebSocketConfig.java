package com.ssafy.crafts.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /**
         * Client에서 websocket연결할 때 사용할 API 경로를 설정해주는 메서드.
         */
        registry.addEndpoint("/ws")     // SockJS 연결 주소
                .setAllowedOriginPatterns("*")      // 서버 환경에 맞게 변경 필요
                .withSockJS();
//        registry.addEndpoint("/stomp/chat")
//                .setAllowedOrigins("http://localhost:8094")
//                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /**
         * @Method Name : enableSimpleBroker
         * 해당 주소를 구독하고 있는 클라이언트에게 메시지 전달
         */
        registry.enableSimpleBroker("/sub");

        /**
         * @Method Name : setApplicationDestinationPrefixes
         * 클라이언트에서 보낸 메시지를 받을 prefix
         */
        registry.setApplicationDestinationPrefixes("/pub");
    }

}
