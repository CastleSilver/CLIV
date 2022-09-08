package com.ssafy.crafts.api.service;

import com.ssafy.crafts.db.entity.Member;
import com.ssafy.crafts.db.repository.querydslRepo.MemberQuerydslRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @FileName : UserServiceImpl
 * @작성자 : 허성은
 * @Class 설명 : 메인페이지 관련 비즈니스 처리 로직을 위한 서비스 구현 정의
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final MemberQuerydslRepository memberQuerydslRepository;
    @Override
    public void changeRoleType(String authId) {
        /**
         * @Method Name : changRoleType
         * @작성자 : 허성은
         * @Method 설명 : 회원의 RoleType을 TEACHER로 변경
         */
        memberQuerydslRepository.changeMemberRoleType(authId);
    }
}
