package com.ssafy.crafts.db.repository.jpaRepo;

import com.ssafy.crafts.db.entity.MBoard;
import com.ssafy.crafts.db.entity.MBoardTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchingRepository extends JpaRepository<MBoard, Integer> {
    List<MBoard> findAll();
    MBoard findById(int id);

}
