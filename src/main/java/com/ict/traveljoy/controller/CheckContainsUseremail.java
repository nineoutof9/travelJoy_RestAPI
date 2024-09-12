package com.ict.traveljoy.controller;


import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ict.traveljoy.security.CustomUserDetails;
import com.ict.traveljoy.security.jwt.util.JwtUtility;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CheckContainsUseremail {
	
	
	private final JwtUtility jwtUtil;

	public String checkContainsUseremail(HttpServletRequest request) {
	    String authorization = request.getHeader("Authorization");
	    String token, useremail;

	    try {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        Object principal = authentication.getPrincipal();
	        
	        String email = null;
	        if (principal instanceof CustomUserDetails) {
	            CustomUserDetails userDetails = (CustomUserDetails) principal;
	            email = userDetails.getUsername();
	        } else if (principal instanceof String) {
	            email = (String) principal;
	        }
	        
	        if (authorization != null && authorization.startsWith("Bearer ")) {
	            token = authorization.split(" ")[1];
	            useremail = jwtUtil.getUserEmailFromToken(token);
	        } else if (email != null) {
	            useremail = email;
	        } else {
	            useremail = "aaa@aaa.com";
	            System.out.println("no useremail");
	        }
	    } catch (Exception e) {
	        useremail = "aaa@aaa.com";
	        e.printStackTrace();
	        System.out.println(useremail);
	    }

	    System.out.println("check useremail: "+useremail);
	    if(useremail.equals("anonymousUser")) {
	    	useremail=null;
	    }
	    return useremail;
	}
}
