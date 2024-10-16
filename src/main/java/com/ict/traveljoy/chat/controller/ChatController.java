package com.ict.traveljoy.chat.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ict.traveljoy.chat.service.ChatRoomDTO;
import com.ict.traveljoy.chat.service.ChatRoomService;
import com.ict.traveljoy.chat.service.EnterChatRoomDTO;
import com.ict.traveljoy.chat.service.MessageDTO;
import com.ict.traveljoy.chat.service.MessageService;
import com.ict.traveljoy.controller.CheckContainsUseremail;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
	private final ChatRoomService chatroomService;
	private final MessageService messageService;
	private final CheckContainsUseremail checkUser;
	

	// topic 받기 요청
	// 방문자의 경우 temp반환하여, python에서 temp인 경우 db저장 X
	@GetMapping("/topic")
	public ResponseEntity<String> getChatRoomTopic(HttpServletRequest request){
		String useremail = checkUser.checkContainsUseremail(request);
		try {
			String chatroomTopic = chatroomService.getChatRoomTopic(useremail);
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(chatroomTopic);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	// 채팅 종료 시
	// chatroom.isActive(0)으로 바꾸기
	@PutMapping("/topic")
	public ResponseEntity<Object> closeChatRoom(HttpServletRequest request) {
		String useremail = checkUser.checkContainsUseremail(request);
		try {
			boolean success = chatroomService.closeChatRoom(useremail);
			if(success) return new ResponseEntity<>(HttpStatus.ACCEPTED);
			else return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// 채팅 내역 불러오기 - 회원 목록
	@GetMapping("/users")
	public ResponseEntity<List<Map<String,Object>>> getAllChatRoom(HttpServletRequest request) {
		String useremail = checkUser.checkContainsUseremail(request);
		// 권한 확인하기
//		List<Map<String,Object>>
		try {
			List<Map<String,Object>> enteredChatrooms = chatroomService.getAllChatRoom(useremail);
			System.out.println(enteredChatrooms.get(0).get("chatroom"));
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(enteredChatrooms);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
			
	}
	
	// 채팅 내역 불러오기 - 회원별 채팅 내용
	// paramerter로 chatroomid받음
	@GetMapping("/user")
	public ResponseEntity<List<MessageDTO>> getMessagesByUser(HttpServletRequest request){
		String useremail = checkUser.checkContainsUseremail(request);
		String topic = request.getParameter("topic");
		try {
			List<MessageDTO> messages = messageService.getAllMessagesByUser(useremail,topic);
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(messages);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	// 채팅 내역 불러오기 - 관리자 뷰, 채팅방 아이디로 불러오기
	@GetMapping("/{chatroom_id}")
	public ResponseEntity<List<Map<String,Object>>> getMessagesByChatRoomId(@PathVariable("chatroom_id") String chatroom_id, HttpServletRequest request){
		String useremail = checkUser.checkContainsUseremail(request);
		
		try {
			List<Map<String,Object>> messages = messageService.getAllMessagesByChatRoom(Long.parseLong(chatroom_id),useremail);
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(messages);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
}