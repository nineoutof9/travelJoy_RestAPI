package com.ict.traveljoy.chat.service;

import com.ict.traveljoy.chat.repository.ChatRoom;
import com.ict.traveljoy.chat.repository.ChatRoomRepository;
import com.ict.traveljoy.chat.repository.EnterChatRoom;
import com.ict.traveljoy.chat.repository.EnterChatRoomRepository;
import com.ict.traveljoy.users.repository.UserRepository;
import com.ict.traveljoy.users.repository.Users;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
	
	private final ChatRoomRepository chatRoomRepository;
	private final EnterChatRoomRepository enterChatRoomRepository;
	private final UserRepository userRepository;
	private final ObjectMapper objectMapper;
	
	
	public String getChatRoomTopic(String useremail) {
		Users user = userRepository.findByEmail(useremail).get();
		
		// if 잇으면 그거 돌려주기
		EnterChatRoom isEntered = enterChatRoomRepository.existsByUser_Id(user.getId());
		ChatRoom chatroom = isEntered.getChatRoom();
		return chatroom.getId().toString();
		// 없으면 생성해서 돌려주기

	}

}
