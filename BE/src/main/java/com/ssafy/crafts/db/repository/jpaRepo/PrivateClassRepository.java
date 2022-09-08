package com.ssafy.crafts.db.repository.jpaRepo;

import com.ssafy.crafts.db.entity.ClassInfo;
import com.ssafy.crafts.db.entity.PrivateClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivateClassRepository extends JpaRepository<PrivateClass, Integer> {
}
