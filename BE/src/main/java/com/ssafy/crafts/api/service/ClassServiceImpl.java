package com.ssafy.crafts.api.service;

import com.ssafy.crafts.api.request.ClassInfoRequest;
import com.ssafy.crafts.api.response.ClassInfoResponse;
import com.ssafy.crafts.api.response.ClassJoinResponse;
import com.ssafy.crafts.db.entity.ClassInfo;
import com.ssafy.crafts.db.entity.Member;
import com.ssafy.crafts.db.repository.jpaRepo.MemberRepository;
import com.ssafy.crafts.db.repository.querydslRepo.CategoryQuerydslRepository;
import com.ssafy.crafts.db.repository.jpaRepo.ClassInfoRepository;
import com.ssafy.crafts.db.repository.querydslRepo.ClassInfoQuerydslRepository;
import com.ssafy.crafts.db.repository.querydslRepo.HashtagQuerydslRepository;
import com.ssafy.crafts.db.repository.querydslRepo.MemberQuerydslRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @FileName : ClassServiceImpl
 * @작성자 : 허성은
 * @Class 설명 : 수업 관련 비즈니스 처리 로직을 위한 서비스 구현 정의
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService{
    static SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    static SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy. MM. dd. a hh:mm:ss", Locale.KOREA);

    private final ClassInfoRepository classInfoRepository;
    private final ClassInfoQuerydslRepository classInfoQuerydslRepository;
    private final MemberRepository memberRepository;
    private final FileUploadService fileUploadService;
    private final MemberQuerydslRepository memberQuerydslRepository;
    private final CategoryQuerydslRepository categoryQuerydslRepository;
    private final HashtagQuerydslRepository hashtagQuerydslRepository;

    @Override
    public void insertClassInfo(ClassInfoRequest classInfoRequest, MultipartFile thumbnail) throws ParseException {
        /**
         * @Method Name : insertClassInfo
         * @작성자 : 허성은
         * @Method 설명 : 수업 생성
         */
        String thumbnailUrl = null;
        try {
            /* S3에 업로드 */
            thumbnailUrl = fileUploadService.upload(thumbnail);
        } catch(Exception e) {
            e.printStackTrace();
        }
        log.info("수업 생성 서비스");
        // String -> Date
        Date classDate = inputFormat.parse(classInfoRequest.getClassDatetime());
        classInfoRequest.setClassImgUrl(thumbnailUrl);

        ClassInfo classInfo = ClassInfo.builder()
                .teacher(memberQuerydslRepository.findMemberByAuthId(classInfoRequest.getTeacherId()).get())
                .category(categoryQuerydslRepository.findCategoryById(classInfoRequest.getCategoryId()))
                .classDatetime(Timestamp.valueOf(timeStampFormat.format(classDate)))
                .className(classInfoRequest.getClassName())
                .headcount(classInfoRequest.getHeadcount())
                .price(classInfoRequest.getPrice())
                .content(classInfoRequest.getContent())
                .classImg(classInfoRequest.getClassImgUrl())
                .level(classInfoRequest.getLevel())
                .classStatus(ClassInfo.ClassStatus.EXPECTED)
                .build();

//        List<HashtagRequest> taggingList = classInfoRequest.getTaggingRequest();
//
//        for(int i = 0; i < taggingList.size(); i++){
//            classInfo.addTagging(hashtagQuerydslRepository.findHashtagById(taggingList.get(i).getHashtagId()).get());
//        }

        classInfoRepository.save(classInfo);
    }

    @Override
    public ClassInfoResponse findClassInfoById(int id) {
        /**
         * @Method Name : findClassInfoById
         * @작성자 : 허성은
         * @Method 설명 : 수업 아이디로 수업 찾기
         */
        ClassInfo classInfo = Optional.ofNullable(classInfoQuerydslRepository.findClassInfoById(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "수업 정보가 존재하지 않습니다."));

        return ClassInfoResponse.builder()
                .classId(classInfo.getId())
                .teacherNickname(classInfo.getTeacher().getNickname())
                .memberCnt(classInfo.getMembers().size())
                .category(classInfo.getCategory().getContent())
                .className(classInfo.getClassName())
                .price(classInfo.getPrice())
                .headcount(classInfo.getHeadcount())
                .classDatetime(classInfo.getClassDatetime())
                .content(classInfo.getContent())
                .classImg(classInfo.getClassImg())
                .classStatus(classInfo.getClassStatus().toString())
                .level(classInfo.getLevel())
                .regdate(classInfo.getRegdate())
                .build();
    }

    @Override
    public ClassJoinResponse joinClassByMemberId(int id, String memberId) {
        /**
         * @Method Name : joinClassByMemberId
         * @작성자 : 허성은
         * @Method 설명 : 수업 참여하기
         */
        ClassInfo classInfo = Optional.ofNullable(classInfoQuerydslRepository.findClassInfoById(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "수업 정보가 존재하지 않습니다."));
        Optional<Member> member = memberQuerydslRepository.findMemberByAuthId(memberId);
        if(!member.isPresent())
            new ResponseStatusException(HttpStatus.NOT_FOUND, "회원 정보가 존재하지 않습니다.");
        // 인원수 다 찼으면 거절
        if(classInfo.getHeadcount() <= classInfo.getMembers().size())
            new ResponseStatusException(HttpStatus.FORBIDDEN, "해당 수업은 신청 인원이 마감되었습니다.");
        // 선생님과 동일한 아이디면 거절
        if(classInfo.getTeacher().getId() == memberId)
            new ResponseStatusException(HttpStatus.FORBIDDEN, "본인이 개설한 수업을 신청할 수 없습니다.");
        // 중복 수강신청이면 거절
        if(classInfo.getMembers().contains(member.get()))
            new ResponseStatusException(HttpStatus.FORBIDDEN, "이미 신청한 수업입니다.");
        log.info("수업 신청");
        classInfo.getMembers().add(member.get());
        classInfoRepository.save(classInfo);
        return ClassJoinResponse.builder().memberCnt(classInfo.getMembers().size()).build();
    }

    @Override
    public List<ClassInfoResponse> findClassListByRegdate() {
        /**
         * @Method Name : findClassListByRegdate
         * @작성자 : 허성은
         * @Method 설명 : 수업 생성순으로 수업 리스트 조회
         */
        List<ClassInfo> classInfoList = classInfoQuerydslRepository.findClassInfoByRegdate();
        return MainServiceImpl.classInfoToDto(classInfoList) != null? MainServiceImpl.classInfoToDto(classInfoList) : Collections.emptyList();
    }

    @Override
    public List<ClassInfoResponse> findExpectedClassListByTeacherId(String authId) {
        /**
         * @Method Name : findExpectedClassListByTeacherId
         * @작성자 : 허성은
         * @Method 설명 : 선생님 아이디로 예정된 수업 리스트를 조회.
         */
        List<ClassInfo> classInfoList = classInfoQuerydslRepository.findExpectedClassListByTeacherId(authId);
        return MainServiceImpl.classInfoToDto(classInfoList) != null? MainServiceImpl.classInfoToDto(classInfoList) : Collections.emptyList();
    }

    @Override
    public List<ClassInfoResponse> findEndedClassListByTeacherId(String authId) {
        /**
         * @Method Name : findEndedClassListByTeacherId
         * @작성자 : 허성은
         * @Method 설명 : 선생님 아이디로 종료된 수업 리스트를 조회.
         */
        List<ClassInfo> classInfoList = classInfoQuerydslRepository.findEndedClassListByTeacherId(authId);
        return MainServiceImpl.classInfoToDto(classInfoList) != null? MainServiceImpl.classInfoToDto(classInfoList) : Collections.emptyList();
    }

    @Override
    public List<ClassInfoResponse> findExpectedClassListByMemberId(String authId) {
        /**
         * @Method Name : findExpectedClassListByMemberId
         * @작성자 : 허성은
         * @Method 설명 : 회원 아이디로 예정된 수업 리스트를 조회.
         */
        List<ClassInfo> classInfoList = classInfoQuerydslRepository.findExpectedClassListByMemberId(authId);
        return MainServiceImpl.classInfoToDto(classInfoList) != null? MainServiceImpl.classInfoToDto(classInfoList) : Collections.emptyList();
    }

    @Override
    public List<ClassInfoResponse> findEndedClassListByMemberId(String authId) {
        List<ClassInfo> classInfoList = classInfoQuerydslRepository.findEndedClassListByMemberId(authId);
        return MainServiceImpl.classInfoToDto(classInfoList) != null? MainServiceImpl.classInfoToDto(classInfoList) : Collections.emptyList();
    }
}
