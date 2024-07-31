package com.ict.traveljoy.controller.ask;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
//@RequiredArgsConstructor
@Tag(name="문의 API",description = "사용자")
@RequestMapping("/ask")
public class AskController{
	
	@GetMapping("/chat/{select}")
	public String chatbot(@PathVariable String select) {
		if(select=="chatbot")
			return "챗봇과 연결";
		else if(select=="agent")
			return "상담원과 연결";
		else
			return "종료";
	}

	
	//채팅방 재입장
	@GetMapping("/chat/{userId}")
	public String getChatRoomByUserId() {
		
		return "userid에 해당하는 chatroom id";
	}
	
	@PostMapping("/chat/{userId}")
	public String postMethodName() {

		
		
		return "새로운 채팅방 생성";
	}
	
	
	
	
	
	
	
}
