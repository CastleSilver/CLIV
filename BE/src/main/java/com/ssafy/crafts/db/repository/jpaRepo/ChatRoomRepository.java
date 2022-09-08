package com.ssafy.crafts.db.repository.jpaRepo;

import com.ssafy.crafts.db.entity.ChatRoom;
import com.ssafy.crafts.db.entity.MBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {

}
