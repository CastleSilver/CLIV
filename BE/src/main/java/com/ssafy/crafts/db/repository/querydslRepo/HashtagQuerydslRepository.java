package com.ssafy.crafts.db.repository.querydslRepo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.crafts.db.entity.Hashtag;
import com.ssafy.crafts.db.entity.QHashtag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HashtagQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    QHashtag qHashtag = QHashtag.hashtag;

    public Optional<Hashtag> findHashtagById(int id) {
        Hashtag hashtag = jpaQueryFactory.select(qHashtag).from(qHashtag)
                .where(qHashtag.id.eq(id)).fetchOne();
        return Optional.ofNullable(hashtag);
    }
}
