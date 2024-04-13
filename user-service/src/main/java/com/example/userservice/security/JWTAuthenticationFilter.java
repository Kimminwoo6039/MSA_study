package com.example.userservice.security;

import com.example.userservice.dto.RequestLogin;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

/**
 * 시큐 리티 로그인 타는 곳
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Logger log = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
//        setFilterProcessesUrl("/login");
        setUsernameParameter("email");
        setPasswordParameter("password");
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        log.debug("디버그 : attemptAuthentication 호출됨");

        try {
            ObjectMapper om = new ObjectMapper();
            RequestLogin signInRequestDto = om.readValue(request.getInputStream(), RequestLogin.class);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    signInRequestDto.getEmail(),signInRequestDto.getPassword()
            );

            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            return authenticate;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    // 로그인 실패
    /// TODO : 4. userId, password db 조회후 없으면 로그인 실패
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        CustomResponseUtil.fail(response,"로그인 실패", HttpStatus.UNAUTHORIZED);
        System.out.println("로그인 실패");
    }
    // TODO : authentication 이 잘작동하면 successfulAuthentication 호출된다 . try 부분
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.debug("디버그 : 로그인이 된거임 호출됨");
        System.out.println("로그인 완료");
        ///TODO : 토큰을 생성하고 리턴함.. 뿌려줌


        LoginUser loginUser = (LoginUser)authResult.getPrincipal();
        String jwtToken = JwtProcess.create(loginUser);
//        response.addHeader(jwtVo.HEADER,jwtToken);
//        HttpSession session = request.getSession();
////        session.setAttribute("key",loginUser);
        response.setHeader(JwtVo.HEADER,jwtToken);
        response.setHeader("userId", loginUser.getUser().getUserId());
        //request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
//        response.sendRedirect("/admin/dashboard");
//        IDTOKEN = jwtToken;
//        log.info("jwtToken : {}" , jwtToken);

//        JoinResponse.LoginResDto loginResDto = new JoinResponse.LoginResDto(loginUser.getUser());
//        log.info("결과 : {}" , loginResDto.toString());

        /// TODO :JSON 으로 반환
//        CustomResponseUtil.success(response,loginUser);
        //request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);

//        response.setHeader(HttpStatus.OK);
    }


}
