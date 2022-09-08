package com.ssafy.crafts.api.service;

import com.ssafy.crafts.api.response.ClassInfoResponse;
import com.ssafy.crafts.api.response.MainResponse;
import com.ssafy.crafts.db.entity.ClassInfo;
import com.ssafy.crafts.db.repository.querydslRepo.ClassInfoQuerydslRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @FileName : MainServiceImpl
 * @작성자 : 허성은
 * @Class 설명 : 수업 리스트 관련 비즈니스 처리 로직을 위한 서비스 구현 정의
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    private final ClassInfoQuerydslRepository classInfoQuerydslRepository;
    @Override
    public MainResponse findAllClassList(String authId) {
        /**
         * @Method Name : findAllClassList
         * @작성자 : 허성은
         * @Method 설명 : 메인 화면 진입시 모든 수업 리스트를 보여줌
         */
        List<ClassInfoResponse> madeCL = findMadeClassList(authId);
        List<ClassInfoResponse> appliedCL = findAppliedClassList(authId);
//        List<ClassInfoResponse> categoryCL = findClassListByCategory(categoryId);
        List<ClassInfoResponse> classTimeCL = findClassListByClassTime();
        List<ClassInfoResponse> headcountCL = findClassListByHeadcount();
        List<ClassInfoResponse> allCL = findClassListAll();
        return MainResponse.builder()
                .madeCL(madeCL != null ? madeCL : Collections.emptyList())
                .appliedCL(appliedCL != null ? appliedCL : Collections.emptyList())
//                .categoryCL(categoryCL != null ? categoryCL : Collections.emptyList())
                .classTimeCL(classTimeCL != null ? classTimeCL : Collections.emptyList())
                .headcountCL(headcountCL != null ? headcountCL : Collections.emptyList())
                .all(allCL != null ? allCL : Collections.emptyList())
                .build();
    }
    @Override
    public List<ClassInfoResponse> findClassListAll() {
        List<ClassInfo> classInfoList = classInfoQuerydslRepository.findClassInfoAll();
        return classInfoToDto(classInfoList) != null? classInfoToDto(classInfoList) : Collections.emptyList();
    }
    @Override
    public List<ClassInfoResponse> findMadeClassList(String authId) {
        List<ClassInfo> classInfoList = classInfoQuerydslRepository.findClassInfoByTeacherId(authId);
        return classInfoToDto(classInfoList) != null? classInfoToDto(classInfoList) : Collections.emptyList();
    }
    @Override
    public List<ClassInfoResponse> findAppliedClassList(String authId) {
        List<ClassInfo> classInfoList = classInfoQuerydslRepository.findClassInfoByMemberId(authId);
        return classInfoToDto(classInfoList) != null? classInfoToDto(classInfoList) : Collections.emptyList();
    }
    @Override
    public List<ClassInfoResponse> findClassListByCategory(int categoryId) {
        List<ClassInfo> classInfoList = classInfoQuerydslRepository.findClassInfoByCategory(categoryId);
        return classInfoToDto(classInfoList) != null? classInfoToDto(classInfoList) : Collections.emptyList();
    }
    @Override
    public List<ClassInfoResponse> findClassListByClassTime() {
        List<ClassInfo> classInfoList = classInfoQuerydslRepository.findClassInfoByClassTime();
        return classInfoToDto(classInfoList) != null? classInfoToDto(classInfoList) : Collections.emptyList();
    }
    @Override
    public List<ClassInfoResponse> findClassListByHeadcount() {
        List<ClassInfo> classInfoList = classInfoQuerydslRepository.findClassInfoByHeadcount();
        return classInfoToDto(classInfoList) != null? classInfoToDto(classInfoList) : Collections.emptyList();
    }

    public static List<ClassInfoResponse> classInfoToDto(List<ClassInfo> list){
        /**
         * @Method Name : classInfoToDto
         * @작성자 : 허성은
         * @Method 설명 : ClassInfoList 속 엔티티를 DTO인 ClassInfoResponse로 바꿔서 반환
         */
        List<ClassInfoResponse> result =new ArrayList<>();
        for (ClassInfo classInfo: list) {
            result.add(ClassInfoResponse.builder()
                    .classId(classInfo.getId())
                    .category(classInfo.getCategory().getContent())
                    .teacherNickname(classInfo.getTeacher().getNickname())
                    .memberCnt(classInfo.getMembers().size())
                    .className(classInfo.getClassName())
                    .classDatetime(classInfo.getClassDatetime())
                    .headcount(classInfo.getHeadcount())
                    .price(classInfo.getPrice())
                    .content(classInfo.getContent())
                    .classImg(classInfo.getClassImg())
                    .level(classInfo.getLevel())
                    .classStatus(classInfo.getClassStatus().toString())
                    .regdate(classInfo.getRegdate())
                    .build());
        }
        return result;
    }
}
