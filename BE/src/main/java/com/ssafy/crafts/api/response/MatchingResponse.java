package com.ssafy.crafts.api.response;

import com.ssafy.crafts.db.entity.MBoard;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * @FileName : MatchingInfoResponse
 * @작성자 : 김민주
 * @Class 설명 : 매칭 정보 조회 API 요청에 대한 리스폰스 바디 정의
 */
@Getter
@ToString
@Builder
public class MatchingResponse {
    private int id;     // PK
    private String title;   // 제목
    private String wantedDay;   // 원하는 수업 날짜
    private String teacherGender;   // 강사성별
    private String content;     // 내용
    private String authId;      // 작성자 아이디
    private String categoryContent;     // 카데고리명
    private boolean matStatus;     // 매칭 완료 여부
    private int price; // 가격
    private Timestamp regDate;      // 작성시간
}
