package com.ssafy.crafts.db.repository.jpaRepo;

import com.ssafy.crafts.db.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<Auth, String> {

}
