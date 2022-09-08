package com.ssafy.crafts.api.service;

import com.ssafy.crafts.api.response.NotiResponse;
import com.ssafy.crafts.common.model.repository.EmitterRepository;
import com.ssafy.crafts.db.entity.Member;
import com.ssafy.crafts.db.entity.Notification;
import com.ssafy.crafts.db.repository.jpaRepo.MemberRepository;
import com.ssafy.crafts.db.repository.jpaRepo.NotificationRepository;
import com.ssafy.crafts.db.repository.querydslRepo.NotificationQuerydslRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @FileName : NotificationServiceImpl
 * @작성자 : 허성은
 * @Class 설명 : SSE 알림 기능 관련 비즈니스 처리 로직을 위한 서비스 구현 정의
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationQuerydslRepository notificationQuerydslRepository;
    private final MemberRepository memberRepository;

    @Override
    public void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data) {
        /**
         * @Method Name : sendNotification
         * @작성자 : 허성은
         * @Method 설명 : 알림을 보낸다.
         */
        try {
            log.info("알림 전송");
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
        }
    }

    @Override
    public boolean hasLostData(String lastEventId) {
        /**
         * @Method Name : hasLostData
         * @작성자 : 허성은
         * @Method 설명 : 클라이언트가 미수신한 이벤트가 있는지 검사한다.
         */
        return !lastEventId.isEmpty();
    }

    @Override
    public void sendLostData(String lastEventId, String authId, String emitterId, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByMemberId(String.valueOf(authId));
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendNotification(emitter, entry.getKey(), emitterId, entry.getValue()));
    }
    @Override
    public String makeTimeIncludeId(String authId) {
        /**
         * @Method Name : makeTimeIncludeId
         * @작성자 : 허성은
         * @Method 설명 : 회원 아이디와 현재 시간을 포함하여 emitterId를 생성한다.
         */
        return authId + "_" + System.currentTimeMillis();
    }

    @Override
    @Async
    public SseEmitter subscribe(String authId) {
        /**
         * @Method Name : subscribe
         * @작성자 : 허성은
         * @Method 설명 : Emitter를 생성 후 어떤 회원이 생성하였는지에 대한 정보를 포함하여 저장한다.
         */

        // 회원 아이디를 포함하여 emitterId를 생성한다.
        String emitterId = makeTimeIncludeId(authId);
        Long timeout = 60L * 1000L * 60L; // 1시간

        // 생성한 emitter를 저장한다.
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(timeout));
        //emitter 시간이 지난 후 repo에서 삭제한다.
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        // 503 에러를 방지하기 위해 더미 이벤트를 전송한다.
        String eventId = makeTimeIncludeId(authId);
        sendNotification(emitter, eventId, emitterId, "EventStream Created. [userId=" + authId + "]");
        log.info(authId);

        return emitter;
    }

    @Override
    @Async
    public void send(String authId, Notification.NotiType notificationType, String message) {
        /**
         * @Method Name : send
         * @작성자 : 허성은
         * @Method 설명 : 이벤트가 발생하면 비동기 처리 방식으로 알림을 전송한다.
         */
        Notification notification =
                notificationRepository.save(createNotification(memberRepository.getOne(authId), notificationType, message));

        String eventId = authId + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByMemberId(authId);
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    sendNotification(emitter, eventId, key, NotiResponse.create(notification));
                }
        );
    }

    @Override
    public Notification createNotification(Member receiver, Notification.NotiType notificationType, String content) {
        /**
         * @Method Name : createNotification
         * @작성자 : 허성은
         * @Method 설명 : 알림 entity 생성
         */
        return Notification.builder()
                .receiver(receiver)
                .notiType(notificationType)
                .message(content)
                .isRead(false)
                .build();
    }

    @Override
    public List<NotiResponse> findAllNotifications(String authId) {
        /**
         * @Method Name : findAllNotifications
         * @작성자 : 허성은
         * @Method 설명 : 회원에 대한 모든 알림을 조회한다.
         */
        List<Notification> notifications = notificationQuerydslRepository.findAllByAuthId(authId);
        try {
            return notifications.stream()
                    .map(NotiResponse::create)
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "오류가 발생했습니다.");
        }finally {
            if (notifications.stream()!=null){
                notifications.stream().close();
            }
        }
    }

    @Override
    public List<NotiResponse> readNotification(int notificationId, String authId) {
        /**
         * @Method Name : readNotification
         * @작성자 : 허성은
         * @Method 설명 : 클릭된 알림을 읽음 처리 한 후, 전체 알림 리스트를 반환한다.
         */
        //알림을 받은 사람의 id 와 알림의 id 를 받아와서 해당 알림을 찾는다.
        Optional<Notification> notification = notificationRepository.findById(notificationId);
        Notification checkNotification = notification.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        // 해당 알림을 읽음 처리 한다.
        checkNotification.read();
        notificationRepository.save(checkNotification);
        return findAllNotifications(authId);
    }
}