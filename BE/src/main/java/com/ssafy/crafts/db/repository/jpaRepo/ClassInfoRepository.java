package com.ssafy.crafts.db.repository.jpaRepo;

import com.ssafy.crafts.db.entity.ClassInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassInfoRepository extends JpaRepository<ClassInfo, Integer> {
}
