package com.example.userservice.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * 시큐 리티 인증 처리 실패시
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        System.out.println("인증");
        String requestURI = request.getRequestURI();

//            if (requestURI.contains("admin")) {
//                CustomResponseUtil.fail(response,"관리자로 로그인을 진행해주세요", HttpStatus.UNAUTHORIZED);
//            } else {
//                CustomResponseUtil.fail(response,"로그인을 진행해주세요",HttpStatus.BAD_REQUEST);
//            }
//        response.sendRedirect("/");
    }
}
