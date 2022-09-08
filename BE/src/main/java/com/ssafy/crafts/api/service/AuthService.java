package com.ssafy.crafts.api.service;

import com.ssafy.crafts.api.request.AuthRequest;
import com.ssafy.crafts.api.response.AuthResponse;
import com.ssafy.crafts.common.util.JwtTokenProvider;
import com.ssafy.crafts.db.entity.Auth;
import com.ssafy.crafts.db.entity.Member;
import com.ssafy.crafts.db.repository.jpaRepo.AuthRepository;
import com.ssafy.crafts.db.repository.querydslRepo.MemberQuerydslRepository;
import com.ssafy.crafts.db.repository.jpaRepo.MemberRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * @FileName : AuthService
 * @작성자 : 허성은
 * @Class 설명 : 카카오 로그인 관련 비즈니스 로직 처리를 위한 서비스 구현 정의
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final ClientKakao clientKakao;
    private final MemberQuerydslRepository memberQuerydslRepository;
    private final MemberRepository memberRepository;
    private final AuthRepository authRepository;
    private final JwtTokenProvider jwtTokenProvider;
    @Transactional
    public AuthResponse login(AuthRequest authRequest) {
        /**
         * @Method Name : login
         * @작성자 : 허성은
         * @Method 설명 : 카카오 로그인 시 새로운 회원이면 회원가입을, 아니라면 로그인을 진행
         */
        //access token을 가지고 ClientKakao을 호출하여 카카오의 사용자 정보를 조회
        Member kakaoMember = clientKakao.getUserData(authRequest.getAccessToken());
        String id = kakaoMember.getAuth().getAuthId();
        Optional<Member> member = memberQuerydslRepository.findMemberByAuthId(id);

        // 사용자 정보 조회 후, 내려받은 사용자식별 ID 값으로 DB에서 이미 가입된 사람인지를 판별 후, 새로운 유저라면 저장, JWT 토큰을 발급
        // 회원가입
        if (!member.isPresent()) {
            Auth auth = Auth.builder()
                    .authId(id)
                    .email(kakaoMember.getAuth().getEmail())
                    .build();
            authRepository.save(auth);

            Member newbie = Member.builder()
                    .auth(authRepository.getOne(id))
                    .gender(kakaoMember.getGender())
                    .profileImage(kakaoMember.getProfileImage())
                    .status(Member.Status.ACTIVE)
                    .roleType(Member.RoleType.MEMBER)
                    .nickname("회원_"+id)
                    .build();

            memberRepository.save(newbie);

        }

        // 기존 사용자라면 토큰 만료로 인한 재요청이기 때문에 DB와의 커넥션 없이 바로 새로운 토큰만 발급하여 반환
        Member user = memberQuerydslRepository.findMemberByAuthId(id).get();
//        AuthToken appToken = authTokenProvider.createUserAppToken(id, nickname);

        return jwtTokenProvider.createToken(user);
    }
    public Member getMember(){
        return new Member();
    }
    public String getAuthId(String token) {
        /**
         * @Method Name : getAuthId
         * @작성자 : 허성은
         * @Method 설명 : 토큰에서 AuthId를 꺼내서 반환
         */
        Claims claims = jwtTokenProvider.getAllClaims(token);
        if (claims == null) {
            return null;
        }

        try {
            Member member =  memberQuerydslRepository.findMemberByAuthId(claims.getSubject()).get();
            return member.getId();

        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다.");
        }
    }
}
