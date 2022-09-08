package com.ssafy.crafts.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @FileName : ClassInfoRequest
 * @작성자 : 허성은
 * @Class 설명 : 수업 관련 API 요청에 필요한 리퀘스트 바디 정의
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("ClassInfoRequest")
@Builder
public class ClassInfoRequest {
    @ApiModelProperty(name = "teacherId", hidden = true)
    String teacherId;
    @ApiModelProperty(name = "categoryId", example = "3")
    int categoryId;
    @ApiModelProperty(name = "taggingRequest")
    List<HashtagRequest> taggingRequest;
    @ApiModelProperty(name = "className", example = "수업 제목")
    String className;
    @ApiModelProperty(name = "headcount", example = "8")
    int headcount;
    @ApiModelProperty(name = "price", example = "50000")
    int price;
    @ApiModelProperty(name = "content", example = "수업 설명")
    String content;
    @ApiModelProperty(name = "classImgUrl", hidden = true)
    String classImgUrl;
    @ApiModelProperty(name = "level", example = "5")
    int level;
    @ApiModelProperty(name = "classDatetime", hidden = true)
    String classDatetime;

    public void setClassImgUrl(String classImgUrl) {
        this.classImgUrl = classImgUrl;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
}
