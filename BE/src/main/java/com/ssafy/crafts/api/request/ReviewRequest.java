package com.ssafy.crafts.api.request;

import com.ssafy.crafts.db.entity.PhraseReview;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @FileName : ReviewRequest
 * @작성자 : 김민주
 * @Class 설명 : 리뷰 관련 API 요청에 필요한 리퀘스트 바디 정의
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("ReviewRequest")
@Builder
public class ReviewRequest {

    @ApiModelProperty(name = "authId", hidden = true)
    private String authId;      // 작성자 id

    @ApiModelProperty(name = "score", example = "별점")
    private int score;      // 별점

    @ApiModelProperty(name = "textRv", example = "텍스트 리뷰")
    private String textRv;      // 텍스트 리뷰

    @ApiModelProperty(name = "classId", example = "수업 id")
    private int classId;    // 수업 id

    @ApiModelProperty(name = "prList")
    private List<PhraseReviewRequest> prList;  // 선택한 리뷰문구id 리스트

    public void setAuthId(String authId){
        this.authId = authId;
    }

}
