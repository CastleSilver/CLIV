package com.ssafy.crafts.db.repository.querydslRepo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.crafts.db.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRoomQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    QMBoard qmBoard = QMBoard.mBoard;
    QChatRoom qChatRoom = QChatRoom.chatRoom;
    QMBoardTeacher qmBoardTeacher = QMBoardTeacher.mBoardTeacher;

    public List<ChatRoom> findAllRoomByAuthId(String authId) {
        return jpaQueryFactory.selectFrom(qChatRoom)
                .where(qChatRoom.mBoardTeacher.mBoard.member.id.eq(authId)).fetch();
    }

    public ChatRoom findByMtId(int mtId){
        return jpaQueryFactory.selectFrom(qChatRoom)
                .where(qChatRoom.mBoardTeacher.id.eq(mtId))
                .fetchOne();
    }


//    public List<Integer> findMBoardIdListByTeacherId(String teacherId) {
//        List<Integer> mBoardIdList = jpaQueryFactory.select(qmBoardTeacher.mBoard.id)
//                .from(qmBoardTeacher)
//                .where(qmBoardTeacher.teacher.id.eq(teacherId)).fetch();
//        return mBoardIdList;
//    }
//
//    public List<MBoard> findMBoardListByAuthId(String authId) {
//        return jpaQueryFactory.select(qmBoard).from(qmBoard)
//                    .where(qmBoard.member.id.eq(authId)).fetch();
//    }
//
//    @Transactional
//    public void updateMatStatus(int mboardId){
//        jpaQueryFactory.update(qmBoard)
//                .set(qmBoard.matStatus, true)
//                .where(qmBoard.id.eq(mboardId))
//                .execute();
//    }

//    public List<MBoard> findMBoardByteacherId(String teacherId) {
//
//
//        return jpaQueryFactory.select(qmBoard).from(qmBoard)
//                .join(qmBoard.mBoardTeacher, qmBoardTeacher)
//                .where(qmBoard.mBoardTeacher.teacher.id.eq(teacherId))
//                .fetch();
//    }

}
