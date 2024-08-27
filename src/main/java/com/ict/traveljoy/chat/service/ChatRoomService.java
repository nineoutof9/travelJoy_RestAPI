package com.ict.traveljoy.chat.service;

import com.ict.traveljoy.chat.repository.ChatRoom;
import com.ict.traveljoy.chat.repository.ChatRoomRepository;
import com.ict.traveljoy.chat.repository.EnterChatRoom;
import com.ict.traveljoy.chat.repository.EnterChatRoomRepository;
import com.ict.traveljoy.users.repository.UserRepository;
import com.ict.traveljoy.users.repository.Users;

import java.util.ArrayList;
import java.util.List;

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
		
		// if 있으면 돌려주기
		if(enterChatRoomRepository.countByUser_Id(user.getId())==1) {
			EnterChatRoom isEntered = enterChatRoomRepository.findByUser_Id(user.getId());
			ChatRoom chatroom = isEntered.getChatRoom();
			chatroom.setIsActive(1);
			chatroom = chatRoomRepository.save(chatroom);
			return chatroom.getId().toString();
		}
		else if(enterChatRoomRepository.countByUser_Id(user.getId())>=1) { //권한확인하기
			return "";
		}
		else { // 없으면 생성해서 돌려주기
			ChatRoom chatroom = new ChatRoom();
			chatroom.setIsActive(1);
			chatroom = chatRoomRepository.save(chatroom);
			
			EnterChatRoom newEnteredChatRoom = new EnterChatRoom();
			newEnteredChatRoom.setChatRoom(chatroom);
			newEnteredChatRoom.setUser(user);
			newEnteredChatRoom = enterChatRoomRepository.save(newEnteredChatRoom);
			return chatroom.getId().toString();
		}
		
	}


	public List<ChatRoomDTO> getAllChatRoom() {
		
		List<ChatRoom> chatrooms = chatRoomRepository.findAll();
		List<ChatRoomDTO> chatroomDTOs = new ArrayList<ChatRoomDTO>();
		for(ChatRoom room : chatrooms) {
			if(room.getIsDelete()==0) {
				chatroomDTOs.add(ChatRoomDTO.toDTO(room));
			}
		}
		
		return chatroomDTOs;
	}


	// chatroom.isActive(0)으로 바꾸기
	public boolean closeChatRoom(String useremail) {
		// TODO Auto-generated method stub
		return false;
	}


	
	

}
