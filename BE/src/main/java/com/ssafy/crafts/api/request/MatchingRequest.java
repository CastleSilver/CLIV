package com.ssafy.crafts.api.request;

import com.ssafy.crafts.db.entity.MBoard;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.sql.Timestamp;

/**
 * @FileName : MatchingRequest
 * @작성자 : 김민주
 * @Class 설명 : 매칭 관련 API 요청에 필요한 리퀘스트 바디 정의
 */
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@ApiModel("MatchingRequest")
@Builder
public class MatchingRequest {

    @ApiModelProperty(name = "authId", hidden = true)
    private String authId;      // 작성자 id

    @ApiModelProperty(name = "title", example = "제목")
    private String title;       // 제목

    @ApiModelProperty(name = "wantedDay", example = "원하는 수업 날짜")
    private String wantedDay;   // 원하는 수업 날짜

    @ApiModelProperty(name = "teacherGender", example = "원하는 강사성별")
    private String teacherGender;   // 강사 성별

    @ApiModelProperty(name = "content", example = "내용")
    private String content;     // 내용

    @ApiModelProperty(name = "regDate", hidden = true)
    private Timestamp regDate;      // 작성시간

    @ApiModelProperty(name = "matStatus", hidden = true)
    private boolean matStatus;    // 매칭 완료 여부

    @ApiModelProperty(name = "categoryId", example = "카테고리 id")
    private int categoryId;     // 카테고리 아이디

    private int price; // 가격

    public void setAuthId(String authId){
        this.authId = authId;
    }

//    public MBoard toEntity(){
//        return MBoard.builder()
//                .title(title)
//                .wantedDay(wantedDay)
//                .teacherGender(teacherGender)
//                .content(content)
//                .category(categoryQuerydslRepository.findCategoryById(getCategoryId()).get())
//                .member(memberQuerydslRepository.findMemberByAuthId(getAuthId()))
//                .build();
//    }
}
