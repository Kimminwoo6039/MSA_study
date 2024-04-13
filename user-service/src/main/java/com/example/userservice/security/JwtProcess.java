package com.example.userservice.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.userservice.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class JwtProcess {

    private final Logger logger = LoggerFactory.getLogger(JwtProcess.class);

    /**
     * 토큰 생성
     */

    public static String create(LoginUser loginUser) {
        String token = JWT.create()
                .withSubject(loginUser.getUsername()) /// TODO : 토큰 제목
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtVo.EXPIRATTION_TIME)) /// TODO : 현재시간 + 토큰 5초
//                .withClaim("role", loginUser.getUser().getRole() + "") /// TODO : 권한 등촉
                .sign(Algorithm.HMAC512(JwtVo.SECRET)); /// TODO : 알고리즘화 ( security )
        return JwtVo.TOKEN_PREFIX + token;
    }

    /**
     * 토큰 검증
     */
    /**
     * 토큰 검증
     * ( return 되는 LoginUser 객체를 강제로 시큐리티 세션에 직접 주입할 예정 )
     */
    public static LoginUser verify(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtVo.SECRET)).build().verify(token);
        Long id = decodedJWT.getClaim("id").asLong();
        String role = decodedJWT.getClaim("role").asString();
        UserEntity user = UserEntity.builder().id(id).build(); /// TODO : "role" 이런식으로 들어옴
        LoginUser loginUser = new LoginUser(user);
        return loginUser;
    }

}
