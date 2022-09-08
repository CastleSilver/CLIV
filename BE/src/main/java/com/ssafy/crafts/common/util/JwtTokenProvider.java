package com.ssafy.crafts.common.util;

import com.ssafy.crafts.api.response.AuthResponse;
import com.ssafy.crafts.common.exception.TokenValidFailedException;
import com.ssafy.crafts.db.entity.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {
    private final String secretKey = "a605clivtjddmsdlaksemsxhzmsejrlfdjdigksekrhrmfjsmsepdlwjdehaus";


//    public JwtTokenProvider(@Value("${jwt.token.secret-key}") String secretKey) {
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
//    }
    // Access Token 유효기간 - 하루
    private static final Long accessTokenValidTime = 24 * 60 * 60 * 1000L;

    // Refresh Token 유효기간 - 7일
    private static final Long refreshTokenValidTime = 7 * 24 * 60 * 60 * 1000L;

//    @Autowired
//    private UserDetailsService memberDetailsService;

    // 토큰 생성
    public AuthResponse createToken(Member member) {
        byte[] keyBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key secretKey = Keys.hmacShaKeyFor(keyBytes);
        log.info("토큰 생성");
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");

        Claims claims = Jwts.claims().setSubject(member.getId());
        claims.put("nickname", member.getNickname());
        claims.put("role", member.getRoleType().toString());

        Date now = new Date();
        log.info(String.valueOf(now));
        String accessToken = Jwts.builder()
                .setHeader(headers)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                .signWith(Keys.hmacShaKeyFor(keyBytes), SignatureAlgorithm.HS256)
                .compact();
//        System.out.println(accessToken);
        String refreshToken = Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(Keys.hmacShaKeyFor(keyBytes), SignatureAlgorithm.HS256)
                .compact();

        return AuthResponse.builder()
                .appToken(accessToken)
                .accessTokenExpiresIn(accessTokenValidTime)
                .refreshToken(refreshToken)
                .build();
    }

    public String getUserPk(String token) {
        log.info("토큰에서 아이디 추출 = " + Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject());
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public Authentication getAuthentication(String token) {
        log.info("JWT 토큰에서 인증 정보 조회");
        if(validateToken(token) != null) {

            Claims claims = getAllClaims(token);
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(new String[]{claims.get("role").toString()})
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            User principal = new User(claims.getSubject(), "", authorities);

            return new UsernamePasswordAuthenticationToken(principal, token, authorities);
        } else {
            throw new TokenValidFailedException();
        }
    }

    public String resolveToken(HttpServletRequest request) {
        log.info("resolveToken = "+request.getHeader("Authorization"));
        return request.getHeader("Authorization");
    }

    public String convertToken(HttpServletRequest request) {
        String token = resolveToken(request);
        token = token.replaceAll("Bearer ", "");
        return token;
    }
    // 토큰의 유효성 + 만료일자 확인
    public Claims validateToken(String jwtToken) {
        log.info("토큰 유효성 확인");
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다");
        } catch (MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다");
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return null;
    }

    public String getAccessTokenPayload(String accessToken) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken)
                .getBody().getSubject();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = getAllClaims(token);
        return Long.valueOf(String.valueOf(claims.getSubject()));
    }

    public Claims getAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            e.printStackTrace();
            // The JWT MUST contain exactly two period characters. 등
            throw e;
        }
    }
}
