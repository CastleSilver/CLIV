package com.ssafy.crafts.api.response;

import com.ssafy.crafts.db.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @FileName : NotiResponse
 * @작성자 : 허성은
 * @Class 설명 : 실시간 알림 이벤트 발생에 대한 리스폰스 바디
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotiResponse {
    private int id;
    private String message;
    private String notiType;
    private String authId;
    private boolean isRead;
    private Timestamp regDate;

    public static NotiResponse create(Notification notification) {
        return new NotiResponse(notification.getNotiId(), notification.getMessage(),
                notification.getNotiType().toString(), notification.getReceiver().getId(), notification.getIsRead(), notification.getRegdate());
    }
}
