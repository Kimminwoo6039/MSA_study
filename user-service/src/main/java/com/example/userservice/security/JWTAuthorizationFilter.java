package com.example.userservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 매번 Header 검증
 */
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("<<< doFilterInternal");

        if(isHeaderVerify(request,response)) {
            // 토큰이 존재함
            try {

            String token = request.getHeader(JwtVo.HEADER).replace(JwtVo.TOKEN_PREFIX,"");
            LoginUser loginUser = JwtProcess.verify(token);

            // 임시 세션 ( UserDetails 타입 or username )
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                 //CustomResponseUtil.fail(response,"토큰이 만료되었습니다.", HttpStatus.BAD_REQUEST);
                 return;
            }

        }
        filterChain.doFilter(request, response);
    }

    private boolean isHeaderVerify(HttpServletRequest request,HttpServletResponse response) {

        String header = request.getHeader(JwtVo.HEADER);
        if(header == null || !header.startsWith(JwtVo.TOKEN_PREFIX)) {
            return false;
        } else {
            return true;
        }
    }
}
