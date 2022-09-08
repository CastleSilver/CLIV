package com.ssafy.crafts.api.service;

import com.ssafy.crafts.api.request.PhraseReviewRequest;
import com.ssafy.crafts.api.request.ReviewRequest;
import com.ssafy.crafts.api.response.ReviewResponse;
import com.ssafy.crafts.db.entity.PhraseReview;
import com.ssafy.crafts.db.entity.Review;
import com.ssafy.crafts.db.repository.jpaRepo.*;
import com.ssafy.crafts.db.repository.querydslRepo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @FileName : ReviewServiceImpl
 * @작성자 : 김민주
 * @Class 설명 : 리뷰 기능 관련 비즈니스 처리 로직을 위한 서비스 구현 정의
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberQuerydslRepository memberQuerydslRepository;
    private final ClassInfoRepository classInfoRepository;
    private final ReviewQuerydslRepository reviewQuerydslRepository;
    private final PhraseReviewRepository phraseReviewRepository;

    @Override
    public void createReview(ReviewRequest reviewRequest) {
        /**
         * @Method Name : createReview
         * @작성자 : 김민주
         * @Method 설명 : 리뷰 생성
         */

        List<PhraseReviewRequest> phraseReviewRequests = reviewRequest.getPrList();
        List<PhraseReview> prList = new ArrayList<>();

        for (PhraseReviewRequest phraseReviewRequest : phraseReviewRequests){
            prList.add(phraseReviewRepository.findById(phraseReviewRequest.getPrId()).get());
        }

        Review review = Review.builder()
                .score(reviewRequest.getScore())
                .textRv(reviewRequest.getTextRv())
                .prList(prList)
                .classInfo(classInfoRepository.findById(reviewRequest.getClassId()).get())
                .member(memberQuerydslRepository.findMemberByAuthId(reviewRequest.getAuthId()).get())
                .build();

        reviewRepository.save(review);
    }

    @Override
    public Review findByAuthIdAndClassId(String authId, int classId) {
        return reviewQuerydslRepository.findByAuthIdAndClassId(authId, classId);
    }

    @Override
    public List<ReviewResponse> getReviewListByAuthId(String authId) {
        /**
         * @Method Name : getReviewListByAuthId
         * @작성자 : 김민주
         * @Method 설명 : 작성한 리뷰 목록 조회
         */
        List<Review> reviewList = reviewQuerydslRepository.findAllByAuthId(authId);
        List<ReviewResponse> list = new ArrayList<>();

        for(Review review : reviewList) {
            list.add(ReviewResponse.builder()
                .id(review.getId())
                .score(review.getScore())
                .prList(review.getPrList())
                .textRv(review.getTextRv())
                .classId(review.getClassInfo().getId())
                .className(review.getClassInfo().getClassName())
                .teacherId(review.getClassInfo().getTeacher().getId())
                .nickname(review.getClassInfo().getTeacher().getNickname())
                .categoryId(review.getClassInfo().getCategory().getId())
                .category(review.getClassInfo().getCategory().getContent())
                .build());
        }
        return list;
    }

    @Override
    public List<ReviewResponse> getReviewListByTeacherId(String teacherId) {
        /**
         * @Method Name : getReviewListByTeacherId
         * @작성자 : 김민주
         * @Method 설명 : 받은 리뷰 목록 조회
         */
        List<Review> reviewList = reviewQuerydslRepository.findAllByTeacherId(teacherId);
        List<ReviewResponse> list = new ArrayList<>();

        for(Review review : reviewList) {
            list.add(ReviewResponse.builder()
                .id(review.getId())
                .score(review.getScore())
                .prList(review.getPrList())
                .textRv(review.getTextRv())
                .classId(review.getClassInfo().getId())
                .className(review.getClassInfo().getClassName())
                .teacherId(review.getClassInfo().getTeacher().getId())
                .nickname(review.getClassInfo().getTeacher().getNickname())
                .categoryId(review.getClassInfo().getCategory().getId())
                .category(review.getClassInfo().getCategory().getContent())
                .build());
        }
        return list;
    }

}
