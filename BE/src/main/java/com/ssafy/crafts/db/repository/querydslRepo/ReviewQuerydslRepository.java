package com.ssafy.crafts.db.repository.querydslRepo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.crafts.db.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    QReview qReview = QReview.review;

    public Review findByAuthIdAndClassId(String authId, int classId) {
        return jpaQueryFactory.selectFrom(qReview)
                .where(qReview.member.id.eq(authId)
                .and(qReview.classInfo.id.eq(classId)))
                .fetchOne();
    }

    public List<Review> findAllByAuthId(String authId){
        return jpaQueryFactory.selectFrom(qReview)
                .where(qReview.member.id.eq(authId))
                .fetch();
    }

    public List<Review> findAllByTeacherId(String teacherId){
        return jpaQueryFactory.selectFrom(qReview)
                .where(qReview.classInfo.teacher.id.eq(teacherId))
                .fetch();
    }

}
