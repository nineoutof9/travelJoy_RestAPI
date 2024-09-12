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

		
		if(useremail==null || !userRepository.existsByEmail(useremail)) {
			return "temp";
		}
		
		Users user = userRepository.findByEmail(useremail).get();
		
		// if 있으면 돌려주기
		if(enterChatRoomRepository.countByUser_Id(user.getId())==1) {


			EnterChatRoom isEntered = enterChatRoomRepository.findByUser_Id(user.getId());
			ChatRoom chatroom = isEntered.getChatRoom();
			chatroom.setIsActive(1);
			chatroom = chatRoomRepository.save(chatroom);
			return chatroom.getId().toString();
		}


		else if(enterChatRoomRepository.countByUser_Id(user.getId())>=1 && user.getPermission().equalsIgnoreCase("ROLE_ADMIN")) { //권한확인하기
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

	  
	// chatroom.isActive(0)으로 바꾸기
	public boolean closeChatRoom(String useremail) {
		// TODO Auto-generated method stub
		return false;
	}


	public List<EnterChatRoomDTO> getAllChatRoom(String useremail) {
		Users user = userRepository.findByEmail(useremail).get();
		
		if(user.getPermission().equalsIgnoreCase("ROLE_ADMIN")) {
			
			List<EnterChatRoom> enteredRooms = enterChatRoomRepository.findAll();
			List<EnterChatRoomDTO> enteredRoomDTOs = new ArrayList<EnterChatRoomDTO>();
			
			for(int i=0;i<enteredRooms.size();i++) {
				if(enteredRooms.get(i).getChatRoom().getIsDelete()==0
						&& enteredRooms.get(i).getUser().getId()!=user.getId()) {
					// delete 상태가 아니고, user(admin 입장)와 같지 않은 채팅방만 가져오기(중복방지)
					enteredRoomDTOs.add(EnterChatRoomDTO.toDTO(enteredRooms.get(i)));
				}
			}

			return enteredRoomDTOs;
		}
		else
			throw new IllegalArgumentException("권한이 없습니다.");
		
	}

	


	
	

}
