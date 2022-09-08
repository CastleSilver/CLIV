package com.ssafy.crafts.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @FileName : FileResponse
 * @작성자 : 허성은
 * @Class 설명 : CK5 이미지 업로드 요청에 대한 리스폰스 바디
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileResponse {
    private boolean uploaded;
    private String url;
}
