package com.ict.traveljoy.controller.help;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
//@RequiredArgsConstructor
@Tag(name="문의 API",description = "사용자")
@RequestMapping("/help")
public class HelpController{

	@GetMapping("/select") //react에서 구현
	public String select(@PathVariable String select) {
		if(select=="agent")
			return "agent";
		else return "chatbot";
	}
	
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
	@GetMapping("/chat/room")
	public String getChatRoomByUserId() {
		return "userid에 해당하는 chatroom id";
	}
	
	
	
	
	
	
}
