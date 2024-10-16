// 필요한 클래스와 인터페이스를 import합니다.
package com.ict.traveljoy.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.security.CustomUserDetails;
import com.ict.traveljoy.security.InputUser;
import com.ict.traveljoy.security.jwt.refreshtoken.RefreshToken;
import com.ict.traveljoy.security.jwt.service.RefreshService;
import com.ict.traveljoy.security.jwt.util.JwtUtility;
import com.ict.traveljoy.users.repository.Users;
import com.ict.traveljoy.users.service.UserDTO;
import com.ict.traveljoy.users.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

// Lombok의 @Slf4j 어노테이션을 사용하여 로깅을 간편하게 합니다.
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final Long accessExpiredMs;
    private final Long refreshExpiredMs;

    private final UserService userService;
    private final RefreshService refreshService;

    private final AuthenticationManager authenticationManager;
    private final JwtUtility jwtUtility;

    // 생성자를 통해 AuthenticationManager와 JWTUtil의 인스턴스를 주입받습니다.
    public LoginFilter(UserService userService, RefreshService refreshService, AuthenticationManager authenticationManager, JwtUtility jwtUtil) {
        this.userService = userService;
        this.refreshService = refreshService;
        this.authenticationManager = authenticationManager;
        this.jwtUtility = jwtUtil;
        refreshExpiredMs = 86400000L;
        accessExpiredMs = 60000000L;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 요청 본문에서 사용자의 로그인 데이터를 InputUser 객체로 변환합니다.
            InputUser loginData = new ObjectMapper().readValue(request.getInputStream(), InputUser.class);
            // 사용자 이름과 비밀번호를 기반으로 AuthenticationToken을 생성합니다. 이 토큰은 사용자가 제공한 이메일과 비밀번호를 담고 있으며, 이후 인증 과정에서 사용됩니다.
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    loginData.getEmail(), loginData.getPassword());
            // AuthenticationManager를 사용하여 실제 인증을 수행합니다. 이 과정에서 사용자의 이메일과 비밀번호가 검증됩니다.
            return authenticationManager.authenticate(authToken);
        } catch (AuthenticationException e) {
            // 요청 본문을 읽는 과정에서 오류가 발생한 경우, AuthenticationServiceException을 던집니다.
            throw new AuthenticationServiceException("인증 처리 중 오류가 발생했습니다.", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 로그인 성공 시 실행되는 메소드입니다. 인증된 사용자 정보를 바탕으로 JWT를 생성하고, 이를 응답 헤더에 추가합니다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        // 인증 객체에서 CustomUserDetails를 추출합니다.
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        // CustomUserDetails에서 사용자 이름(이메일)을 추출합니다.
        String username = customUserDetails.getUsername();

        // 사용자 이름을 사용하여 JWT를 생성합니다.
        String access = jwtUtility.generateToken(username, "access", accessExpiredMs);
        String refresh = jwtUtility.generateToken(username, "refresh", refreshExpiredMs);

        // RefreshToken 저장 로직
        
        Users user = userService.findEntityByEmail(username);
        userService.userLastLogin(username);
        LocalDateTime now = LocalDateTime.now();
        RefreshToken refreshToken = RefreshToken.builder()
                    .status("activated")
                    .userAgent(request.getHeader("User-Agent"))
                    .user(user)
                    .tokenValue(refresh)
                    .issuedAt(now)
                    .expirationDate(now.plusSeconds(refreshExpiredMs / 1000))
                    .build();
            refreshService.save(refreshToken);

        // 리프레쉬 토큰을 httpOnly 쿠키로 설정
        Cookie refreshCookie = new Cookie("refreshToken", refresh);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/"); // 모든 경로에서 쿠키 접근 가능
        refreshCookie.setMaxAge(refreshExpiredMs.intValue() / 1000); // 쿠키 유효기간 설정
        response.addCookie(refreshCookie);

        // 응답 헤더에 액세스 토큰 추가
        response.addHeader("Authorization", "Bearer " + access);
        // 클라이언트가 Authorization 헤더를 읽을 수 있도록, 해당 헤더를 노출
        response.setHeader("Access-Control-Expose-Headers", "Authorization");

        String isAdmin = customUserDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(", "));
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("username", username);
        responseBody.put("isAdmin", isAdmin);
        responseBody.put("loginType", "email");
        responseBody.put("success", true);
        
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                customUserDetails, null, customUserDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);

        String responseBodyJson = new ObjectMapper().writeValueAsString(responseBody);
        response.setContentType("application/json");
        response.getWriter().write(responseBodyJson);
        response.getWriter().flush();
    }


    // 로그인 실패 시 실행되는 메소드입니다. 실패한 경우, HTTP 상태 코드 401을 반환합니다.
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {

        // failed 객체로부터 최종 원인 예외를 찾습니다.
        Throwable rootCause = failed;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
        }

        // rootCause를 기반으로 오류 메시지를 설정합니다.
        String message;
        if (rootCause instanceof UsernameNotFoundException) {
            message = "존재하지 않는 이메일입니다.";
        } else if (rootCause instanceof BadCredentialsException) {
            message = "잘못된 비밀번호입니다.";
        } else if (rootCause instanceof DisabledException) {
            message = "계정이 비활성화되었습니다.";
        } else if (rootCause instanceof LockedException) {
            message = "계정이 잠겨 있습니다.";
        } else {
            // 다른 예외들을 처리
            message = "인증에 실패했습니다.";
        }

        // 응답 데이터를 준비합니다.
        Map<String, Object> responseData = new HashMap<>();
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        responseData.put("timestamp", LocalDateTime.now().toString());
        responseData.put("status", HttpStatus.BAD_REQUEST.value());
        responseData.put("error", "Unauthorized");
        responseData.put("message", message);
        responseData.put("path", request.getRequestURI());

        // 응답을 보냅니다.
        try {
            String jsonResponse = new ObjectMapper().writeValueAsString(responseData);
            response.getWriter().write(jsonResponse);
            response.getWriter().flush();
        } catch (IOException ignored) {
        }
    }
}
