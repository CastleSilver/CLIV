package com.ssafy.crafts.api.service;

import com.ssafy.crafts.api.request.ClassInfoRequest;
import com.ssafy.crafts.api.request.MatchingTeacherRequest;
import com.ssafy.crafts.api.request.PrivateClassRequest;
import com.ssafy.crafts.api.response.ClassInfoResponse;
import com.ssafy.crafts.db.entity.PrivateClass;
import org.springframework.web.multipart.MultipartFile;

/**
 * @FileName : PrivateClassService
 * @작성자 : 김민주
 * @Class 설명 : 1:1수업 관련 비즈니스 처리 로직을 위한 인터페이스 설정
 */
public interface PrivateClassService {

    PrivateClass createPrivateClass(PrivateClassRequest privateClassRequest);
}
