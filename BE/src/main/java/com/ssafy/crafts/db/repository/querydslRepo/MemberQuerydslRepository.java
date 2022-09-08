package com.ssafy.crafts.db.repository.querydslRepo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.crafts.db.entity.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class MemberQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    QMember qMember = QMember.member;
    QAuth qAuth = QAuth.auth;
    QCategory qCategory = QCategory.category;

    public Optional<Member> findMemberByAuthId(String authId) {
        Member member = jpaQueryFactory.select(qMember).from(qMember)
                .where(qMember.auth.authId.eq(authId)).fetchOne();
        return Optional.ofNullable(member);
    }

    @Transactional
    public void changeMemberRoleType(String authId) {
        jpaQueryFactory.update(qMember)
                .set(qMember.roleType, Member.RoleType.TEACHER)
                .where(qMember.auth.authId.eq(authId))
                .execute();
    }

    public List<Member> findByCategoryAndGender(int categoryId, String gender) {
        return jpaQueryFactory.selectFrom(qMember)
                .join(qCategory).on(qCategory.id.eq(categoryId))
                .where(qMember.gender.eq(gender).and(qMember.roleType.eq(Member.RoleType.TEACHER)))
                .fetch();
    }

}