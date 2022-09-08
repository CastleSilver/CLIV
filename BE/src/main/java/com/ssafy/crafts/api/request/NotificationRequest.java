package com.ssafy.crafts.api.request;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @FileName : ClassInfoRequest
 * @작성자 : 허성은
 * @Class 설명 : 알림 요청에 필요한 리퀘스트 바디 정의
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("NotificationRequest")
@Builder
public class NotificationRequest {
    private String authId;
    private String notiType;
    private String message;
    private String url;
    private Timestamp regDate;
}
