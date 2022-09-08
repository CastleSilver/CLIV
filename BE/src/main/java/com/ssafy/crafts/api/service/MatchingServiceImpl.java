package com.ssafy.crafts.api.service;

import com.ssafy.crafts.api.request.MatchingRequest;
import com.ssafy.crafts.api.response.CategoryResponse;
import com.ssafy.crafts.api.response.MBoardTeacherResponse;
import com.ssafy.crafts.api.response.MatchingResponse;
import com.ssafy.crafts.db.entity.*;
import com.ssafy.crafts.db.repository.jpaRepo.*;
import com.ssafy.crafts.db.repository.querydslRepo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @FileName : MatchingServiceImpl
 * @작성자 : 김민주
 * @Class 설명 : 매칭기능 관련 비즈니스 처리 로직을 위한 서비스 구현 정의
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MatchingServiceImpl implements MatchingService{

    private final MatchingRepository matchingRepository;
    private final MBoardRepository mBoardRepository;
    private final MatchingQuerydslRepository matchingQuerydslRepository;
    private final MBoardTeacherQuerydslRepository mBoardTeacherQuerydslRepository;
    private final MBoardTeacherRepository mBoardTeacherRepository;
    private final MemberQuerydslRepository memberQuerydslRepository;
    private final CategoryQuerydslRepository categoryQuerydslRepository;
    private final CategoryRepository categoryRepository;
    private final NotificationService notificationService;

    @Override
    public int createMBoard(MatchingRequest matchingRequest) {
        /**
         * @Method Name : createMBoard
         * @작성자 : 김민주
         * @Method 설명 : 매칭 요청글 생성
         */

        MBoard mBoard = MBoard.builder()
                .title(matchingRequest.getTitle())
                .wantedDay(matchingRequest.getWantedDay())
                .teacherGender(matchingRequest.getTeacherGender())
                .content(matchingRequest.getContent())
                .regDate(matchingRequest.getRegDate())
                .matStatus(matchingRequest.isMatStatus())
                .category(categoryQuerydslRepository.findCategoryById(matchingRequest.getCategoryId()))
                .member(memberQuerydslRepository.findMemberByAuthId(matchingRequest.getAuthId()).get())
                .price(matchingRequest.getPrice())
                .build();

        matchingRepository.save(mBoard);
        return mBoard.getId();
    }

    @Override
    public void matching(int mBoardId, int categoryId, String gender) {
        /**
         * @Method Name : matching
         * @작성자 : 김민주
         * @Method 설명 : 조건에 일치하는 선생님 매칭 하기
         */

        log.info("요청글의 카데고리와 성별이 일치하는 선생님 조회");
        List<Member> teachers = memberQuerydslRepository.findByCategoryAndGender(categoryId, gender);
        // 요청서 받은 선생님들에게 알림 보내기
        for (Member teacher : teachers) {
            String message = mBoardRepository.findById(mBoardId).get().getTitle();
            notificationService.send(teacher.getId(), Notification.NotiType.MatchingRequest, message);
            MBoardTeacher mBoardTeacher = MBoardTeacher.builder()
                    .mBoard(mBoardRepository.findById(mBoardId).get())
                    .teacher(teacher)
                    .build();
            mBoardTeacherRepository.save(mBoardTeacher);

        }


        for(Member teacher : teachers){

            MBoardTeacher mBoardTeacher = MBoardTeacher.builder()
                    .mBoard(mBoardRepository.findById(mBoardId).get())
                    .teacher(teacher)
                    .build();
            mBoardTeacherRepository.save(mBoardTeacher);
        }
    }


    @Override
    public List<MBoardTeacherResponse> getMBoardTeacherListByTeacherId(String teacherId) {
        /**
         * @Method Name : getMBoardTeacherListByTeacherId
         * @작성자 : 김민주
         * @Method 설명 : 강사 id로 강사가 받은 매칭 요청글 정보 목록 조회
         */
        List<MBoardTeacher> mBoardTeacherList = mBoardTeacherQuerydslRepository.findMBTeacherListByTeacherId(teacherId);
        List<MBoardTeacherResponse> list = new ArrayList<>();

        for(MBoardTeacher mBoardTeacher : mBoardTeacherList){
            if(!mBoardTeacher.getMBoard().isMatStatus()) {
                list.add(MBoardTeacherResponse.builder()
                        .mt_id(mBoardTeacher.getId())
                        .agreeYn(mBoardTeacher.isAgreeYn())
                        .teacherId(mBoardTeacher.getTeacher().getId())
                        .mboardId(mBoardTeacher.getMBoard().getId())
                        .title(mBoardTeacher.getMBoard().getTitle())
                        .wantedDay(mBoardTeacher.getMBoard().getWantedDay())
                        .content(mBoardTeacher.getMBoard().getContent())
                        .nickname(mBoardTeacher.getTeacher().getNickname())
                        .category(mBoardTeacher.getMBoard().getCategory().getContent())
                        .price(mBoardTeacher.getMBoard().getPrice())
                        .regdate(mBoardTeacher.getRegdate())
                        .build());
            }
        }
        return list;
    }

    @Override
    public MatchingResponse findMBoardById(int id) {
        /**
         * @Method Name : findMBoardById
         * @작성자 : 김민주
         * @Method 설명 : 매칭 요청글 id로 매칭 요청글 조회
         */
        MBoard mBoard = matchingRepository.findById(id);

        return MatchingResponse.builder()
                .id(mBoard.getId())
                .title(mBoard.getTitle())
                .wantedDay(mBoard.getWantedDay())
                .teacherGender(mBoard.getTeacherGender())
                .content(mBoard.getContent())
                .authId(mBoard.getMember().getId())
                .categoryContent(mBoard.getCategory().getContent())
                .matStatus(mBoard.isMatStatus())
                .price(mBoard.getPrice())
                .regDate(mBoard.getRegDate())
                .build();
    }

    @Override
    public List<MatchingResponse> findMBoardListByAuthId(String authId) {
        /**
         * @Method Name : findMBoardListByAuthId
         * @작성자 : 김민주
         * @Method 설명 : 회원 id로 매칭 요청글 리스트 조회
         */
        List<MBoard> mBoardList = matchingQuerydslRepository.findMBoardListByAuthId(authId);
        List<MatchingResponse> list = new ArrayList<>();

        for(MBoard mBoard : mBoardList){
            list.add(MatchingResponse.builder()
                        .id(mBoard.getId())
                        .title(mBoard.getTitle())
                        .wantedDay(mBoard.getWantedDay())
                        .teacherGender(mBoard.getTeacherGender())
                        .content(mBoard.getContent())
                        .authId(mBoard.getMember().getId())
                        .categoryContent(mBoard.getCategory().getContent())
                        .matStatus(mBoard.isMatStatus())
                        .price(mBoard.getPrice())
                        .regDate(mBoard.getRegDate())
                        .build());
        }
        return list;
    }

    @Override
    public void updateAgreeYnById(int mtId) {
        /**
         * @Method Name : updateAgreeYnById
         * @작성자 : 김민주
         * @Method 설명 : 매칭_선생님 id로 클래스 개설 동의 여부를 Y로 업데이트
         */
        mBoardTeacherRepository.updateAgreeYn(mtId);
    }

    @Override
    public MBoardTeacherResponse getMBoardTeacherById(int mtId) {
        /**
         * @Method Name : getMBoardTeacherById
         * @작성자 : 김민주
         * @Method 설명 : 매칭_선생님 id로 매칭_선생님 조회
         */

        MBoardTeacher mBoardTeacher = Optional.ofNullable(mBoardTeacherRepository.findById(mtId).get())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "요청글이 존재하지 않습니다."));

        return MBoardTeacherResponse.builder()
                .mt_id(mBoardTeacher.getId())
                .agreeYn(mBoardTeacher.isAgreeYn())
                .teacherId(mBoardTeacher.getTeacher().getId())
                .mboardId(mBoardTeacher.getMBoard().getId())
                .build();
    }

    @Override
    public void updateMatStatus(int mtId) {
        matchingQuerydslRepository.updateMatStatus(mBoardTeacherRepository.findById(mtId).get().getMBoard().getId());
    }

    @Override
    public List<CategoryResponse> getCategoryList() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponse> list = new ArrayList<>();

        for (Category category : categoryList){
            list.add(CategoryResponse.builder()
                    .id(category.getId())
                    .content(category.getContent())
                    .build());
        }

        return list;
    }

}
