package com.ict.traveljoy.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.service.users.UsersDto;
import com.ict.traveljoy.service.users.UsersService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class testController {

	private final UsersService usersService;
	private final ObjectMapper objectMapper;
	
	@GetMapping("/api")
	public String test() {
		
		return "success";
	}
	
	@GetMapping("/api/hello")
	public String test2() {
		return "success hello";
	}
	
	@CrossOrigin
	@PostMapping("/users")
	//1)DTO로 받기
	//public ResponseEntity<UsersDto> signUp(UsersDto dto){ //@RequestParam붙이면 400번대 에러
	//2)Map으로 받기
	public ResponseEntity<UsersDto> signUp(@RequestParam Map map){
		try {
			System.out.println(map.get("kakao"));
			
			//2)맵을 DTO로 변환
			UsersDto dto = objectMapper.convertValue(map, UsersDto.class);
			
			UsersDto insertedDto = usersService.signUp(dto);
			
			return ResponseEntity.ok(insertedDto);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}
