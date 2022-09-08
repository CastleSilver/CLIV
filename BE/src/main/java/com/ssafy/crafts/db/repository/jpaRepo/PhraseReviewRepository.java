package com.ssafy.crafts.db.repository.jpaRepo;

import com.ssafy.crafts.db.entity.PhraseReview;
import com.ssafy.crafts.db.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhraseReviewRepository extends JpaRepository<PhraseReview, Integer> {


}
