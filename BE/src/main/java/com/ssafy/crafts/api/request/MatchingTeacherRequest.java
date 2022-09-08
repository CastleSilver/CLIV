package com.ssafy.crafts.api.request;

import com.ssafy.crafts.db.entity.MBoard;
import com.ssafy.crafts.db.repository.querydslRepo.CategoryQuerydslRepository;
import com.ssafy.crafts.db.repository.querydslRepo.MemberQuerydslRepository;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @FileName : MatchingTeacherRequest
 * @작성자 : 김민주
 * @Class 설명 : 매칭_선생님 관련 API 요청에 필요한 리퀘스트 바디 정의
 */
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@ApiModel("MatchingTeacherRequest")
@Builder
public class MatchingTeacherRequest {

    @ApiModelProperty(name = "authId", hidden = true)
    private String authId;
    private int mtId;
    private String title;       // 제목
    private String wantedDay;   // 원하는 수업 날짜
    private String teacherGender;   // 강사 성별
    private String content;     // 내용
    private int categoryId;     // 카테고리 아이디
    private int price;

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
