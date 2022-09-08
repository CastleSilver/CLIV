package com.ssafy.crafts.db.repository.jpaRepo;

import com.ssafy.crafts.db.entity.ChatMessage;
import com.ssafy.crafts.db.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {

}
