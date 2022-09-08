package com.ssafy.crafts.api.service;

import com.ssafy.crafts.api.request.MatchingRequest;
import com.ssafy.crafts.api.request.ReviewRequest;
import com.ssafy.crafts.api.response.MBoardTeacherResponse;
import com.ssafy.crafts.api.response.MatchingResponse;
import com.ssafy.crafts.api.response.ReviewResponse;
import com.ssafy.crafts.db.entity.Review;

import java.util.List;

/**
 * @FileName : ReviewService
 * @작성자 : 김민주
 * @Class 설명 : 리뷰기능 관련 비즈니스 처리 로직을 위한 인터페이스 설정
 */
public interface ReviewService {

    void createReview(ReviewRequest reviewRequest);
    Review findByAuthIdAndClassId(String authId, int classId);
    List<ReviewResponse> getReviewListByAuthId(String authId);
    List<ReviewResponse> getReviewListByTeacherId(String teacherId);
}
