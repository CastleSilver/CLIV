package com.ssafy.crafts.db.repository.jpaRepo;

import com.ssafy.crafts.db.entity.MBoardTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MBoardTeacherRepository extends JpaRepository<MBoardTeacher, Integer> {

    @Modifying
    @Query(value = "UPDATE MBoardTeacher mt set mt.agreeYn = 'Y' where mt.id = :id")
    void updateAgreeYn(@Param("id") int id);
}
