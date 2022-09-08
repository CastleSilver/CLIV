package com.ssafy.crafts.api.service;

import com.ssafy.crafts.api.request.MatchingRequest;
import com.ssafy.crafts.api.response.CategoryResponse;
import com.ssafy.crafts.api.response.MBoardTeacherResponse;
import com.ssafy.crafts.api.response.MatchingResponse;
import com.ssafy.crafts.db.entity.MBoard;
import com.ssafy.crafts.db.entity.MBoardTeacher;

import java.util.List;

/**
 * @FileName : MatchingService
 * @작성자 : 김민주
 * @Class 설명 : 매칭기능 관련 비즈니스 처리 로직을 위한 인터페이스 설정
 */
public interface MatchingService {

    int createMBoard(MatchingRequest matchingRequest);
    void matching(int mBoardId, int categoryId, String gender);
    List<MBoardTeacherResponse> getMBoardTeacherListByTeacherId(String teacherId);
    MatchingResponse findMBoardById(int id);
    List<MatchingResponse> findMBoardListByAuthId(String authId);
    void updateAgreeYnById(int mtId);
    MBoardTeacherResponse getMBoardTeacherById(int mtId);
    void updateMatStatus(int mtId);
    List<CategoryResponse> getCategoryList();
}
