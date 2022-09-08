package com.ssafy.crafts.api.response;

import com.ssafy.crafts.db.entity.MBoardTeacher;
import io.swagger.annotations.ApiModelProperty;
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
public class MBoardTeacherResponse {
    private int mt_id;     // 선생님_매칭보드 id (PK)

    private boolean agreeYn;   // 클래스 개설 수강생 동의 여부

    private String teacherId;      // 강사 아이디

    private int mboardId;     // 매칭보드 아이디

    @ApiModelProperty(name = "title", example = "제목")
    private String title;   // 제목

    @ApiModelProperty(name = "wantedDay", example = "원하는 수업날짜")
    private String wantedDay;   // 원하는 수업 날짜

    @ApiModelProperty(name = "content", example = "내용")
    private String content;     // 내용

    @ApiModelProperty(name = "authId", example = "작성자 아이디")
    private String nickname;      // 작성자 아이디

    @ApiModelProperty(name = "category", example = "카테고리")
    private String category;  // 카데고리
    private int price;
    private Timestamp regdate;

}
