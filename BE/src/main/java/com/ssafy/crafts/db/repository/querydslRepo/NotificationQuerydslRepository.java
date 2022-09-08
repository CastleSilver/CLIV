package com.ssafy.crafts.db.repository.querydslRepo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.crafts.db.entity.ClassInfo;
import com.ssafy.crafts.db.entity.Notification;
import com.ssafy.crafts.db.entity.QNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NotificationQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    QNotification qNotification = QNotification.notification;

    public List<Notification> findAllByAuthId(String authId) {
        /**
         * @Method Name : findAllByAuthId
         * @작성자 : 허성은
         * @Method 설명 : 회원 아이디로 모든 알림을 조회
         */
        return jpaQueryFactory
                .select(qNotification)
                .from(qNotification)
                .where(qNotification.receiver.id.eq(authId).and(qNotification.isRead.isFalse()))
                .orderBy(qNotification.regdate.desc())
                .fetch();
    }
}
