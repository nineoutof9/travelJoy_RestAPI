package com.ict.traveljoy.chat.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.traveljoy.chat.service.ChatRoomDTO;
import com.ict.traveljoy.chat.service.ChatRoomService;
import com.ict.traveljoy.controller.CheckContainsUseremail;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
	private final ChatRoomService chatroomService;
	private final CheckContainsUseremail checkUser;
	
	@GetMapping("/topic")
	public ResponseEntity<String> getChatRoomTopic(HttpServletRequest request){
		String useremail = checkUser.checkContainsUseremail(request);
		try {
			String chatroomTopic = chatroomService.getChatRoomTopic(useremail);
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(chatroomTopic);
		}
		catch(Exception e) {
			
		}
		
		return null;
	}
}
