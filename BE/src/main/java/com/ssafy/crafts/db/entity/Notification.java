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
@Table(name = "Notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noti_id")
    private int notiId;

    @Column(name = "message", length = 100, nullable = false)
    private String message;

    @Column(name = "noti_uri", length = 50, nullable = true)
    private String notiUrl;

    @Column(nullable = false)
    private Boolean isRead;

    @Enumerated(EnumType.STRING)
    @Column(name = "noti_type", nullable = false, length = 50)
    private NotiType notiType;

    @ManyToOne
    @JoinColumn(name = "auth_id")
    private Member receiver;

    @Column(name = "regdate",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp regdate;

    @Builder
    public Notification(int notiId, String message, String notiUrl, Boolean isRead, NotiType notiType, Member receiver, Timestamp regdate) {
        this.notiId = notiId;
        this.message = message;
        this.notiUrl = notiUrl;
        this.isRead = isRead;
        this.notiType = notiType;
        this.receiver = receiver;
        this.regdate = regdate;
    }

    // ClassStart : 수업 10분 전 알람
    // MatchingRequest : 선생님에게 매칭 요청서가 전달 되었을 때
    // MatchingResponse : 매칭에 대한 답변이 왔을 때
    public static enum NotiType {
        ClassStart, MatchingRequest, MatchingResponse;
    }

    public void read() {
        isRead = true;
    }
}