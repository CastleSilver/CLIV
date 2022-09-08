package com.ssafy.crafts.api.service;

import com.ssafy.crafts.api.response.ClassInfoResponse;
import com.ssafy.crafts.api.response.MainResponse;

import java.util.List;

/**
 * @FileName : MainService
 * @작성자 : 허성은
 * @Class 설명 : 메인 화면 비즈니스 처리 로직을 위한 인터페이스 설정
 */
public interface MainService {
    public MainResponse findAllClassList(String authId);
    public List<ClassInfoResponse> findClassListAll();
    public List<ClassInfoResponse> findMadeClassList(String authId);
    public List<ClassInfoResponse> findAppliedClassList(String authId);
    public List<ClassInfoResponse> findClassListByCategory(int categoryId);
    public List<ClassInfoResponse> findClassListByClassTime();
    public List<ClassInfoResponse> findClassListByHeadcount();

}
