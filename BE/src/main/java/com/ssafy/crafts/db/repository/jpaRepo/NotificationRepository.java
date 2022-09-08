package com.ssafy.crafts.db.repository.jpaRepo;

import com.ssafy.crafts.db.entity.Member;
import com.ssafy.crafts.db.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
