package com.ssafy.crafts.db.repository.querydslRepo;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.crafts.api.response.ClassInfoResponse;
import com.ssafy.crafts.db.entity.ClassInfo;
import com.ssafy.crafts.db.entity.Member;
import com.ssafy.crafts.db.entity.QClassInfo;
import com.ssafy.crafts.db.entity.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

@Repository
@RequiredArgsConstructor
public class ClassInfoQuerydslRepository {
    static SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private final JPAQueryFactory jpaQueryFactory;
    private final MemberQuerydslRepository memberQuerydslRepository;
    QClassInfo qClassInfo = QClassInfo.classInfo;
    QMember qMember = QMember.member;
    public ClassInfo findClassInfoById(int id) {
        /**
         * @Method Name : findClassInfoById
         * @작성자 : 허성은
         * @Method 설명 : 클래스 아이디로 수업 조회
         */
        ClassInfo classInfo = jpaQueryFactory.select(qClassInfo).from(qClassInfo)
                .where(qClassInfo.id.eq(id)).fetchOne();
        return classInfo;
    }

    @Transactional
    public void updateClassStatusToENDED(int classId){
        jpaQueryFactory.update(qClassInfo)
                .set(qClassInfo.classStatus, ClassInfo.ClassStatus.ENDED)
                .where(qClassInfo.id.eq(classId))
                .execute();
    }

    @Transactional
    public void updateClassStatusToLIVE(int classId){
        jpaQueryFactory.update(qClassInfo)
                .set(qClassInfo.classStatus, ClassInfo.ClassStatus.LIVE)
                .where(qClassInfo.id.eq(classId))
                .execute();
    }

    @Transactional
    public void insertSessionId(String sessionId, int classId){
        jpaQueryFactory.update(qClassInfo)
                .set(qClassInfo.sessionId, sessionId)
                .where(qClassInfo.id.eq(classId))
                .execute();
    }
    public List<ClassInfo> findClassInfoAll() {
        /**
         * @Method Name : findClassInfoAll
         * @작성자 : 허성은
         * @Method 설명 : 예정 수업 리스트 전체 조회
         */
        return jpaQueryFactory
                .select(qClassInfo)
                .from(qClassInfo)
                .where(qClassInfo.classStatus.eq(ClassInfo.ClassStatus.EXPECTED))
                .fetch();
    }
    public List<ClassInfo> findClassInfoByCategory(int categoryId) {
        /**
         * @Method Name : findClassInfoByCategory
         * @작성자 : 허성은
         * @Method 설명 : 카테고리 아이디로 수업 리스트 조회
         */
        return jpaQueryFactory
                .select(qClassInfo)
                .from(qClassInfo)
                .where(qClassInfo.category.id.eq(categoryId)
                        .and(qClassInfo.classStatus.eq(ClassInfo.ClassStatus.EXPECTED)))
                .fetch();

    }

    public List<ClassInfo> findClassInfoByClassTime() {
        /**
         * @Method Name : findClassInfoByClassTime
         * @작성자 : 허성은
         * @Method 설명 : 수업 마감 시간이 임박한 수업 4개(변경 가능) 조회
         */
        return jpaQueryFactory
                .select(qClassInfo)
                .from(qClassInfo)
                .where(qClassInfo.classStatus.eq(ClassInfo.ClassStatus.EXPECTED))
                .orderBy(qClassInfo.classDatetime.desc())
                .limit(8)
                .fetch();
    }

    public List<ClassInfo> findClassInfoByHeadcount() {
        /**
         * @Method Name : findClassInfoByHeadcount
         * @작성자 : 허성은
         * @Method 설명 : 인원 마감이 임박한 수업 4개(변경 가능) 조회
         */
        return jpaQueryFactory
                .select(qClassInfo)
                .from(qClassInfo)
                .where(qClassInfo.classStatus.eq(ClassInfo.ClassStatus.EXPECTED))
                .orderBy(qClassInfo.headcount.subtract(qClassInfo.members.size()).asc())
                .limit(8)
                .fetch();
    }

    public List<ClassInfo> findClassInfoByMemberId(String authId) {
        /**
         * @Method Name : findClassInfoByMemberId
         * @작성자 : 허성은
         * @Method 설명 : 회원이 수강신청을 한 수업 리스트 조회
         */
        return jpaQueryFactory
                .select(qClassInfo)
                .from(qClassInfo)
                .leftJoin(qClassInfo.members, qMember)
                .where(qClassInfo.members.contains(qMember)
                        .and(qClassInfo.classStatus.eq(ClassInfo.ClassStatus.EXPECTED)))
                .orderBy(qClassInfo.classDatetime.asc())
                .fetch();
    }

    public List<ClassInfo> findClassInfoByTeacherId(String authId) {
        /**
         * @Method Name : findClassInfoByTeacherId
         * @작성자 : 허성은
         * @Method 설명 : 회원이 개설한 수업 리스트 조회
         */
        return jpaQueryFactory
                .select(qClassInfo)
                .from(qClassInfo)
                .where(qClassInfo.classStatus.eq(ClassInfo.ClassStatus.EXPECTED)
                        .and(qClassInfo.teacher.id.eq(authId)))
                .orderBy(qClassInfo.classDatetime.desc())
                .fetch();
    }

    public List<ClassInfo> findClassInfoByRegdate() {
        /**
         * @Method Name : findClassInfoByRegdate
         * @작성자 : 허성은
         * @Method 설명 : 수업 개설순으로 조회.
         */
        return jpaQueryFactory
                .select(qClassInfo)
                .from(qClassInfo)
                .where(qClassInfo.classStatus.eq(ClassInfo.ClassStatus.EXPECTED))
                .orderBy(qClassInfo.regdate.desc())
                .fetch();
    }

    public List<Member> findClassMemberId(int classId) {
        /**
         * @Method Name : findClassMemberId
         * @작성자 : 허성은
         * @Method 설명 : 수업 신청을 한 학생들의 아이디를 반환.
         */
        return jpaQueryFactory
                .select(qClassInfo.members)
                .from(qClassInfo)
                .where(qClassInfo.id.eq(classId))
                .fetchOne();
    }

    public String findClassNameByClassId(int id) {
        /**
         * @Method Name : findClassNameByClassId
         * @작성자 : 허성은
         * @Method 설명 : 클래스 아이디로 수업 이름 조회
         */
        String className = jpaQueryFactory.select(qClassInfo.className).from(qClassInfo)
                .where(qClassInfo.id.eq(id)).fetchOne();
        return className;
    }

    public String findTeacherIdByClassId(int id) {
        /**
         * @Method Name : findTeacherIdByClassId
         * @작성자 : 허성은
         * @Method 설명 : 클래스 아이디로 선생님 아이디 조회
         */
        String className = jpaQueryFactory.select(qClassInfo.teacher.id).from(qClassInfo)
                .where(qClassInfo.id.eq(id)).fetchOne();
        return className;
    }

    public List<ClassInfo> findExpectedClassListByTeacherId(String authId) {
        /**
         * @Method Name : findExpectedClassListByTeacherId
         * @작성자 : 허성은
         * @Method 설명 : 선생님 아이디로 예정된 수업 리스트를 조회.
         */
        return jpaQueryFactory
                .select(qClassInfo)
                .from(qClassInfo)
                .where(qClassInfo.classStatus.eq(ClassInfo.ClassStatus.EXPECTED)
                        .and(qClassInfo.teacher.id.eq(authId)))
                .orderBy(qClassInfo.classDatetime.desc())
                .fetch();
    }

    public List<ClassInfo> findEndedClassListByTeacherId(String authId) {
        /**
         * @Method Name : findEndedClassListByTeacherId
         * @작성자 : 허성은
         * @Method 설명 : 선생님 아이디로 종료된 수업 리스트를 조회.
         */
        return jpaQueryFactory
                .select(qClassInfo)
                .from(qClassInfo)
                .where(qClassInfo.classStatus.eq(ClassInfo.ClassStatus.ENDED)
                        .and(qClassInfo.teacher.id.eq(authId)))
                .orderBy(qClassInfo.regdate.desc())
                .fetch();
    }

    public List<ClassInfo> findExpectedClassListByMemberId(String authId) {
        /**
         * @Method Name : findExpectedClassListByMemberId
         * @작성자 : 허성은
         * @Method 설명 : 회원 아이디로 예정된 수업 리스트를 조회.
         */
        return jpaQueryFactory
                .select(qClassInfo)
                .from(qClassInfo)
                .where(qClassInfo.classStatus.eq(ClassInfo.ClassStatus.EXPECTED)
                        .and(qClassInfo.members.contains(memberQuerydslRepository.findMemberByAuthId(authId).get())))
                .orderBy(qClassInfo.classDatetime.desc())
                .fetch();
    }

    public List<ClassInfo> findEndedClassListByMemberId(String authId) {
        /**
         * @Method Name : findEndedClassListByTeacherId
         * @작성자 : 허성은
         * @Method 설명 : 회원 아이디로 종료된 수업 리스트를 조회.
         */
        return jpaQueryFactory
                .select(qClassInfo)
                .from(qClassInfo)
                .where(qClassInfo.classStatus.eq(ClassInfo.ClassStatus.ENDED)
                        .and(qClassInfo.members.contains(memberQuerydslRepository.findMemberByAuthId(authId).get())))
                .orderBy(qClassInfo.regdate.desc())
                .fetch();
    }

    public String findSessionIdByClassId(int classId) {
        /**
         * @Method Name : findSessionIdByClassId
         * @작성자 : 허성은
         * @Method 설명 : 수업 아이디로 세션 아이디를 조회
         */
        return jpaQueryFactory
                .select(qClassInfo.sessionId)
                .from(qClassInfo)
                .where(qClassInfo.id.eq(classId))
                .fetchOne();
    }
}
