package com.ict.traveljoy.controller.user;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.image.repository.Image;
import com.ict.traveljoy.security.CustomUserDetails;
import com.ict.traveljoy.security.jwt.refreshtoken.RefreshToken;
import com.ict.traveljoy.security.jwt.service.RefreshService;
import com.ict.traveljoy.security.jwt.util.JwtUtility;
import com.ict.traveljoy.users.repository.UserRepository;
import com.ict.traveljoy.users.repository.Users;
import com.ict.traveljoy.users.service.UserDTO;
import com.ict.traveljoy.users.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	private final ObjectMapper objectMapper;
	private final UserRepository userRepository;
	private final JwtUtility jwtUtility;
	private final RefreshService refreshService;
	
	@Value("${kakao.clientId}")
    private String kakaoClientId;
    @Value("${kakao.redirectUri}")
    private String kakaoRedirectUri;
    @Value("${google.clientId}")
    private String googleClientId;
    @Value("${google.clientSecret}")
    private String googleClientSecret;
    @Value("${google.redirectUri}")
    private String googleRedirectUri;
    @Value("${naver.clientId}")
    private String naverClientId;
    @Value("${naver.clientSecret}")
    private String naverClientSecret;
    @Value("${naver.redirectUri}")
    private String naverRedirectUri;
	
    @GetMapping("/checkemail")
	 public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestParam("email") String email) {
	        try {
	            boolean exists = userService.checkEmailExists(email);
	            return ResponseEntity.ok(Map.of("exists", exists));
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("exists", false));
	        }
	  }
    
	@PostMapping("/register")
	public ResponseEntity<UserDTO> signUp(@RequestBody UserDTO dto){
		try {
			UserDTO insertedDto=userService.signUp(dto);
			return ResponseEntity.ok(insertedDto);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}	
	}
	@PutMapping("/updateprofile")
	public ResponseEntity<Map<String, Object>> updateProfile(
	        @RequestParam(value = "file", required = false) MultipartFile file,
	        @RequestParam Map<String, Object> updatedProfile) {
	    try {
	        // 현재 인증된 사용자를 가져오기
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal(); // CustomUserDetails로 캐스팅
	        String email = userDetails.getUsername(); // 또는 getEmail() 메서드가 있으면 사용

	        // 사용자 프로필 가져오기
	        UserDTO user = userService.findByEmail(email);

	        if (user == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }

	        // 업데이트할 수 있는 필드만 수정
	        if (updatedProfile.containsKey("name")) {
	            user.setName((String) updatedProfile.get("name"));
	        }
	        if (updatedProfile.containsKey("nickname")) {
	            user.setNickname((String) updatedProfile.get("nickname"));
	        }
	        if (updatedProfile.containsKey("introduce")) {
	            user.setIntroduce((String) updatedProfile.get("introduce"));
	        }
	        if (updatedProfile.containsKey("birthDate")) {
	            user.setBirthDate(Date.valueOf((String) updatedProfile.get("birthDate"))); // Date로 변환
	        }
	        if (updatedProfile.containsKey("gender")) {
	            // 문자열을 Boolean으로 변환 후 Integer로 설정
	            String genderStr = (String) updatedProfile.get("gender");
	            Boolean gender = Boolean.valueOf(genderStr);
	            user.setGender(gender);
	        }

	        // 프로필 정보 업데이트
	        userService.updateProfile(user);

	        // 이미지 파일이 있는 경우 프로필 이미지 업데이트
	        if (file != null && !file.isEmpty()) {
	            userService.updateProfileImage(email, file);
	        }

	        // 업데이트된 사용자 프로필 정보를 Map으로 변환하여 응답
	        Map<String, Object> responseData = objectMapper.convertValue(user, Map.class);
	        responseData.keySet().retainAll(List.of("birthDate", "gender", "name", "nickname", "introduce", "profileImageUrl", "email"));

	        return ResponseEntity.ok(responseData);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}

	@GetMapping("/getprofile")
	public ResponseEntity<Map<String, Object>> getProfile() {
	    try {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
	        String email = userDetails.getUsername(); // 또는 getEmail() 메서드가 있으면 사용

	        UserDTO user = userService.findByEmail(email);

	        if (user == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }

	        Map<String, Object> profileData = objectMapper.convertValue(user, Map.class);

	        if (user.getProfileImage() != null) {
	            // Image 엔티티의 imageUrl 필드를 직접 사용하여 반환
	            String imageUrl = user.getProfileImage().getImageUrl();
	            profileData.put("profileImageUrl", imageUrl);
	        }

	        profileData.keySet().retainAll(List.of("birthDate", "gender", "name", "nickname", "introduce", "profileImageUrl", "email"));

	        return ResponseEntity.ok(profileData);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}
	 @GetMapping("/kakao")
	 public void kakaoLogin(@RequestParam(name = "code") String code,HttpServletRequest request, HttpServletResponse response) throws IOException {
	     // 액세스 토큰을 요청하기 위한 URL 및 헤더 설정
	     String tokenUrl = "https://kauth.kakao.com/oauth/token";
	     HttpHeaders tokenHeaders = new HttpHeaders();
	     tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	     String tokenRequestBody = "grant_type=authorization_code"
	             + "&client_id=" + kakaoClientId
	             + "&redirect_uri=" + kakaoRedirectUri
	             + "&code=" + code;

	     // 토큰 요청
	     HttpEntity<String> tokenRequestEntity = new HttpEntity<>(tokenRequestBody, tokenHeaders);
	     RestTemplate restTemplate = new RestTemplate();
	     ResponseEntity<String> tokenResponse = restTemplate.exchange(tokenUrl, HttpMethod.POST, tokenRequestEntity, String.class);

	     // JSON 응답을 Map으로 변환하여 access_token 추출
	     Map<String, Object> tokenMap = objectMapper.readValue(tokenResponse.getBody(), Map.class);
	     String accessToken = (String) tokenMap.get("access_token");

	     // 사용자 정보를 요청하기 위한 URL 및 헤더 설정
	     String userInfoUrl = "https://kapi.kakao.com/v2/user/me";
	     HttpHeaders userInfoHeaders = new HttpHeaders();
	     userInfoHeaders.set("Authorization", "Bearer " + accessToken);

	     // 사용자 정보 요청
	     HttpEntity<String> userInfoRequestEntity = new HttpEntity<>(userInfoHeaders);
	     ResponseEntity<String> userInfoResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, userInfoRequestEntity, String.class);

	     // JSON 응답을 Map으로 변환하여 사용자 이메일 추출
	     Map<String, Object> userInfoMap = objectMapper.readValue(userInfoResponse.getBody(), Map.class);
	     Map<String, Object> kakaoAccount = (Map<String, Object>) userInfoMap.get("kakao_account");
	     String email = kakaoAccount != null && kakaoAccount.containsKey("email") ? (String) kakaoAccount.get("email") : "이메일 정보가 없습니다.";

	     Optional<Users> optionalUser = userRepository.findByEmailAndLoginType(email, "kakao");

	     if (optionalUser.isPresent()) {
	         Users user = optionalUser.get();
	         user.setLastLogin(LocalDateTime.now());
	         user.setSnsAccessToken(accessToken);
	         userRepository.save(user);

	         // JWT 토큰 발급
	         Long accessExpiredMs = 600000L;
	         String accessTokenJwt = jwtUtility.generateToken(email, "access", accessExpiredMs);
	         Long refreshExpiredMs = 86400000L;
	         String refreshTokenJwt = jwtUtility.generateToken(email, "refresh", refreshExpiredMs);

	         RefreshToken refreshToken = RefreshToken.builder()
							.status("activated")
							.userAgent(request.getHeader("User-Agent"))
							.user(user)
							.tokenValue(refreshTokenJwt)
							.issuedAt(LocalDateTime.now())
							.expirationDate(LocalDateTime.now().plusSeconds(refreshExpiredMs / 1000))
							.build();

	         refreshService.save(refreshToken);
	         
	         Cookie refreshCookie = new Cookie("refreshToken", refreshTokenJwt);
	         refreshCookie.setHttpOnly(true);
	         refreshCookie.setPath("/"); // 모든 경로에서 쿠키 접근 가능
	         refreshCookie.setMaxAge(refreshExpiredMs.intValue() / 1000); // 쿠키 유효기간 설정
	         response.addCookie(refreshCookie);
	         
	         // 로그인 성공 후 URL에 토큰 정보 포함
	         String redirectUrl = String.format("http://localhost:3000/user/signin?access=%s&isAdmin=%s&email=%s",
	                 accessTokenJwt, user.getPermission().equalsIgnoreCase("ROLE_ADMIN"), email);

	         response.sendRedirect(redirectUrl);
	     } else {
	    	 boolean isUser = userRepository.existsByEmail(email);
	    	 if(!isUser) {
	    	 UserDTO dto = new UserDTO();
	    	 dto = dto.builder().email(email).loginType("kakao").password("").build();
	    	 userService.signUp(dto);
	         
	    	 
	         // 카카오 로그아웃 처리
	         String logoutUrl = "https://kapi.kakao.com/v1/user/logout";
	         HttpHeaders logoutHeaders = new HttpHeaders();
	         logoutHeaders.set("Authorization", "Bearer " + accessToken);

	         HttpEntity<String> logoutRequestEntity = new HttpEntity<>(logoutHeaders);
	         ResponseEntity<String> logoutResponse = restTemplate.exchange(logoutUrl, HttpMethod.POST, logoutRequestEntity, String.class);

	         response.sendRedirect("http://localhost:3000/user/signin?status=success&message="+URLEncoder.encode("회원가입이 성공하였습니다", StandardCharsets.UTF_8.toString()));
	    	 }
	    	 else {
	    		 response.sendRedirect("http://localhost:3000/user/signin?status=error&message="+URLEncoder.encode("이미 사용중인 이메일입니다", StandardCharsets.UTF_8.toString()));
	    	 }
	     }
	 }
	 @GetMapping("/google")
	 public void googleLogin(@RequestParam(name = "code") String code, HttpServletRequest request, HttpServletResponse response) throws IOException {
	     // 액세스 토큰을 요청하기 위한 URL 및 헤더 설정
	     String tokenUrl = "https://oauth2.googleapis.com/token";
	     HttpHeaders tokenHeaders = new HttpHeaders();
	     tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	     String tokenRequestBody = "grant_type=authorization_code"
	             + "&client_id=" + googleClientId
	             + "&client_secret=" + googleClientSecret
	             + "&redirect_uri=" + googleRedirectUri
	             + "&code=" + code;

	     // 토큰 요청
	     HttpEntity<String> tokenRequestEntity = new HttpEntity<>(tokenRequestBody, tokenHeaders);
	     RestTemplate restTemplate = new RestTemplate();
	     ResponseEntity<String> tokenResponse = restTemplate.exchange(tokenUrl, HttpMethod.POST, tokenRequestEntity, String.class);

	     // JSON 응답을 Map으로 변환하여 access_token 추출
	     ObjectMapper objectMapper = new ObjectMapper();
	     Map<String, Object> tokenMap = objectMapper.readValue(tokenResponse.getBody(), Map.class);
	     String accessToken = (String) tokenMap.get("access_token");

	     // 사용자 정보를 요청하기 위한 URL 및 헤더 설정
	     String userInfoUrl = "https://www.googleapis.com/oauth2/v2/userinfo";
	     HttpHeaders userInfoHeaders = new HttpHeaders();
	     userInfoHeaders.set("Authorization", "Bearer " + accessToken);

	     // 사용자 정보 요청
	     HttpEntity<String> userInfoRequestEntity = new HttpEntity<>(userInfoHeaders);
	     ResponseEntity<String> userInfoResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, userInfoRequestEntity, String.class);

	     // JSON 응답을 Map으로 변환하여 사용자 이메일 추출
	     Map<String, Object> userInfoMap = objectMapper.readValue(userInfoResponse.getBody(), Map.class);
	     String email = userInfoMap.containsKey("email") ? (String) userInfoMap.get("email") : "이메일 정보가 없습니다.";

	     Optional<Users> optionalUser = userRepository.findByEmailAndLoginType(email, "google");

	     if (optionalUser.isPresent()) {
	         Users user = optionalUser.get();
	         user.setLastLogin(LocalDateTime.now());
	         user.setSnsAccessToken(accessToken);
	         userRepository.save(user);

	         // JWT 토큰 발급
	         Long accessExpiredMs = 600000L;
	         String accessTokenJwt = jwtUtility.generateToken(email, "access", accessExpiredMs);
	         Long refreshExpiredMs = 86400000L;
	         String refreshTokenJwt = jwtUtility.generateToken(email, "refresh", refreshExpiredMs);

	         RefreshToken refreshToken = RefreshToken.builder()
	                 .status("activated")
	                 .userAgent(request.getHeader("User-Agent"))
	                 .user(user)
	                 .tokenValue(refreshTokenJwt)
	                 .issuedAt(LocalDateTime.now())
	                 .expirationDate(LocalDateTime.now().plusSeconds(refreshExpiredMs / 1000))
	                 .build();

	         refreshService.save(refreshToken);

	         Cookie refreshCookie = new Cookie("refreshToken", refreshTokenJwt);
	         refreshCookie.setHttpOnly(true);
	         refreshCookie.setPath("/"); // 모든 경로에서 쿠키 접근 가능
	         refreshCookie.setMaxAge(refreshExpiredMs.intValue() / 1000); // 쿠키 유효기간 설정
	         response.addCookie(refreshCookie);

	         // 로그인 성공 후 URL에 토큰 정보 포함
	         String redirectUrl = String.format("http://localhost:3000/user/signin?access=%s&isAdmin=%s&email=%s",
	                 accessTokenJwt, user.getPermission().equalsIgnoreCase("ROLE_ADMIN"), email);

	         response.sendRedirect(redirectUrl);
	     } else {
	    	 boolean isUser = userRepository.existsByEmail(email);
	    	 if(!isUser) {
	         UserDTO dto = new UserDTO();
	         dto = dto.builder().email(email).loginType("google").password("").build();
	         userService.signUp(dto);

	         // Google 로그아웃 처리 (로그아웃 처리가 필요 없다면 생략할 수 있습니다.)
	         String logoutUrl = "https://accounts.google.com/o/oauth2/revoke?token=" + accessToken;
	         HttpHeaders logoutHeaders = new HttpHeaders();
	         HttpEntity<String> logoutRequestEntity = new HttpEntity<>(logoutHeaders);
	         ResponseEntity<String> logoutResponse = restTemplate.exchange(logoutUrl, HttpMethod.GET, logoutRequestEntity, String.class);
	         
	         response.sendRedirect("http://localhost:3000/user/signin?status=success&message="+URLEncoder.encode("회원가입이 성공하였습니다", StandardCharsets.UTF_8.toString()));
	    	 }
	    	 else {
	    		 response.sendRedirect("http://localhost:3000/user/signin?status=error&message="+URLEncoder.encode("이미 사용중인 이메일입니다", StandardCharsets.UTF_8.toString()));
	    	 }
	     }
	 }
	 
	 @GetMapping("/naver")
	 public void naverLogin(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state, HttpServletRequest request, HttpServletResponse response) throws IOException {
	     // 액세스 토큰을 요청하기 위한 URL 및 헤더 설정
	     String tokenUrl = "https://nid.naver.com/oauth2.0/token";
	     HttpHeaders tokenHeaders = new HttpHeaders();
	     tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	     String tokenRequestBody = "grant_type=authorization_code"
	             + "&client_id=" + naverClientId
	             + "&client_secret=" + naverClientSecret
	             + "&redirect_uri=" + naverRedirectUri
	             + "&code=" + code
	             + "&state=" + state;

	     // 토큰 요청
	     HttpEntity<String> tokenRequestEntity = new HttpEntity<>(tokenRequestBody, tokenHeaders);
	     RestTemplate restTemplate = new RestTemplate();
	     ResponseEntity<String> tokenResponse = restTemplate.exchange(tokenUrl, HttpMethod.POST, tokenRequestEntity, String.class);

	     // JSON 응답을 Map으로 변환하여 access_token 추출
	     ObjectMapper objectMapper = new ObjectMapper();
	     Map<String, Object> tokenMap = objectMapper.readValue(tokenResponse.getBody(), Map.class);
	     String accessToken = (String) tokenMap.get("access_token");

	     // 사용자 정보를 요청하기 위한 URL 및 헤더 설정
	     String userInfoUrl = "https://openapi.naver.com/v1/nid/me";
	     HttpHeaders userInfoHeaders = new HttpHeaders();
	     userInfoHeaders.set("Authorization", "Bearer " + accessToken);

	     // 사용자 정보 요청
	     HttpEntity<String> userInfoRequestEntity = new HttpEntity<>(userInfoHeaders);
	     ResponseEntity<String> userInfoResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, userInfoRequestEntity, String.class);

	     // JSON 응답을 Map으로 변환하여 사용자 이메일 추출
	     Map<String, Object> userInfoMap = objectMapper.readValue(userInfoResponse.getBody(), Map.class);
	     Map<String, Object> responseMap = (Map<String, Object>) userInfoMap.get("response");
	     String email = responseMap.containsKey("email") ? (String) responseMap.get("email") : "이메일 정보가 없습니다.";

	     Optional<Users> optionalUser = userRepository.findByEmailAndLoginType(email, "naver");

	     if (optionalUser.isPresent()) {
	         Users user = optionalUser.get();
	         user.setLastLogin(LocalDateTime.now());
	         user.setSnsAccessToken(accessToken);
	         userRepository.save(user);

	         // JWT 토큰 발급
	         Long accessExpiredMs = 600000L;
	         String accessTokenJwt = jwtUtility.generateToken(email, "access", accessExpiredMs);
	         Long refreshExpiredMs = 86400000L;
	         String refreshTokenJwt = jwtUtility.generateToken(email, "refresh", refreshExpiredMs);

	         RefreshToken refreshToken = RefreshToken.builder()
	                 .status("activated")
	                 .userAgent(request.getHeader("User-Agent"))
	                 .user(user)
	                 .tokenValue(refreshTokenJwt)
	                 .issuedAt(LocalDateTime.now())
	                 .expirationDate(LocalDateTime.now().plusSeconds(refreshExpiredMs / 1000))
	                 .build();

	         refreshService.save(refreshToken);

	         Cookie refreshCookie = new Cookie("refreshToken", refreshTokenJwt);
	         refreshCookie.setHttpOnly(true);
	         refreshCookie.setPath("/");
	         refreshCookie.setMaxAge(refreshExpiredMs.intValue() / 1000);
	         response.addCookie(refreshCookie);

	         // 로그인 성공 후 URL에 토큰 정보 포함
	         String redirectUrl = String.format("http://localhost:3000/user/signin?access=%s&isAdmin=%s&email=%s",
	                 accessTokenJwt, user.getPermission().equalsIgnoreCase("ROLE_ADMIN"), email);

	         response.sendRedirect(redirectUrl);
	     } else {
	    	 boolean isUser = userRepository.existsByEmail(email);
	    	 if(!isUser) {
	         UserDTO dto = new UserDTO();
	         dto = dto.builder().email(email).loginType("naver").password("").build();
	         userService.signUp(dto);

	         response.sendRedirect("http://localhost:3000/user/signin?status=success&message="+URLEncoder.encode("회원가입이 성공하였습니다", StandardCharsets.UTF_8.toString()));
	    	 }
	    	 else {
	    		 response.sendRedirect("http://localhost:3000/user/signin?status=error&message="+URLEncoder.encode("이미 사용중인 이메일입니다", StandardCharsets.UTF_8.toString()));
	    	 }
	     }
	 }

	 
}
