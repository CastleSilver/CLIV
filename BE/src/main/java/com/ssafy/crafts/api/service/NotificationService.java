package com.ssafy.crafts.api.service;

import com.ssafy.crafts.api.response.NotiResponse;
import com.ssafy.crafts.db.entity.Member;
import com.ssafy.crafts.db.entity.Notification;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

/**
 * @FileName : NotificationService
 * @작성자 : 허성은
 * @Class 설명 : 알람 관련 비즈니스 처리 로직을 위한 인터페이스 설정
 */
public interface NotificationService {
    String makeTimeIncludeId(String authId);
    void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data);
    boolean hasLostData(String lastEventId);
    void sendLostData(String lastEventId, String authId, String emitterId, SseEmitter emitter);
    SseEmitter subscribe(String authId);
    void send(String authId, Notification.NotiType notificationType, String message);
    Notification createNotification(Member receiver, Notification.NotiType notificationType, String content);
    List<NotiResponse> findAllNotifications(String authId);
    List<NotiResponse> readNotification(int notificationId, String authId);
}