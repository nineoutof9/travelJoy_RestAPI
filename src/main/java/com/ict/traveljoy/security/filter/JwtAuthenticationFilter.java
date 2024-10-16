package com.ict.traveljoy.security.filter;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ict.traveljoy.security.CustomUserDetails;
import com.ict.traveljoy.security.jwt.util.JwtUtility;
import com.ict.traveljoy.users.repository.Users;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter{

	// JWT 관련 유틸리티 메서드를 제공하는 JWTUtil 클래스의 인스턴스를 멤버 변수로 가집니다.
    private final JwtUtility jwtUtility;

    // 생성자를 통해 JWTUtil의 인스턴스를 주입받습니다.
    public JwtAuthenticationFilter(JwtUtility jwtUtility) {
        this.jwtUtility = jwtUtility;
    }
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 요청에서 'Authorization' 헤더를 추출합니다.
        String authorization = request.getHeader("Authorization");
        
        String requestURI = request.getRequestURI();
        if ("/reissue".equals(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }
        
        if ("/logout".equals(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 'Authorization' 헤더가 없거나 Bearer 토큰이 아니면 요청을 계속 진행합니다.
        if( authorization == null || !authorization.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        // Bearer 토큰에서 JWT를 추출합니다.
        String token = authorization.split(" ")[1];

        // 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
        try {
        	jwtUtility.isTokenExpired(token);
        } catch (ExpiredJwtException e) {
            //response body
            PrintWriter writer = response.getWriter();
            writer.print("access token expired");
            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // token에서 category 가져오기
        String category = jwtUtility.getCategoryFromToken(token);
        // 토큰 category 가 access 가 아니 라면 만료 된 토큰 이라고 판단
        if (!category.equals("access")) {

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("invalid access token");

            //response status code
            // 응답 코드를 프론트와 맞추는 부분 401 에러 외 다른 코드로 맞춰서
            // 진행하면 리프레시 토큰 발급 체크를 빠르게 할수 있음
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }


        // 토큰에서 사용자 이메일과 관리자 여부를 추출합니다.
        String userEmail = jwtUtility.getUserEmailFromToken(token);
        String permission = jwtUtility.getPermissionFromToken(token);

        // 인증에 사용할 임시 User 객체를 생성하고, 이메일과 관리자 여부를 설정합니다.
        Users user = new Users();
        user.setEmail(userEmail);
        user.setPassword("tempPassword"); // 실제 인증에서는 사용되지 않는 임시 비밀번호를 설정합니다.
        user.setPermission(permission);

        // User 객체를 기반으로 CustomUserDetails 객체를 생성합니다.
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        // Spring Security의 Authentication 객체를 생성하고, SecurityContext에 설정합니다.
        // 이로써 해당 요청에 대한 사용자 인증이 완료됩니다.
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 필터 체인을 계속 진행합니다.
        filterChain.doFilter(request,response);
	}

}
