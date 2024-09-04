package com.ict.traveljoy.chat.service;

import com.ict.traveljoy.chat.repository.ChatRoom;
import com.ict.traveljoy.chat.repository.ChatRoomRepository;
import com.ict.traveljoy.chat.repository.EnterChatRoom;
import com.ict.traveljoy.chat.repository.EnterChatRoomRepository;
import com.ict.traveljoy.chat.repository.Message;
import com.ict.traveljoy.chat.repository.MessageRepository;

import com.ict.traveljoy.report.repository.Report;
import com.ict.traveljoy.report.service.ReportDTO;

import com.ict.traveljoy.users.repository.UserRepository;
import com.ict.traveljoy.users.repository.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

	private final MessageRepository messageRepository;
	private final ChatRoomRepository chatRoomRepository;
	private final EnterChatRoomRepository enterChatRoomRepository;
	private final UserRepository userRepository;
	
	private final ObjectMapper objectMapper;

	public List<MessageDTO> getAllMessagesByUser(String useremail,String topic) { //topic = 채팅방 id
		// 유저 아이디로 유저 구분, 유저의 채팅방 참여 여부 확인, 채팅방 활성화 여부 확인, 채팅방아이디에 따라 메세지 가져오기 
		Users user = userRepository.findByEmail(useremail).get(); //요청 user
		ChatRoom chatroom = chatRoomRepository.findById(Long.parseLong(topic)).get();	//요청 topic, 채팅방

		List<Message> messages = new ArrayList<Message>();
		
		if(enterChatRoomRepository.existsByUser_Id(user.getId())) {
			EnterChatRoom isEntered = enterChatRoomRepository.findByUser_Id(user.getId());

			//채팅방의 유저와 요청한 유저가 같은지확인
			// topic과 enterchatroom의 유저의 chatroom.id가 같은지 확인
			if(chatroom.equals(isEntered.getChatRoom()) && chatroom.getIsActive()==1) {

				messages = messageRepository.findAllByChatRoom_Id(chatroom.getId());
				return messages.stream().map(msg->MessageDTO.toDTO(msg)).collect(Collectors.toList());
			}
			else throw new IllegalArgumentException("활성화된 채팅방 없음");
		}
//		List<Message> messages = messageRepository.findAllByUser_Id(user.getId());
		
		
		return null;
	}

	public List<MessageDTO> getAllMessagesByChatRoom(long chatroomId, String useremail) {
		// 채팅방아이디로 구분, 채팅방 있으면 돌려주기
		
		Users user = userRepository.findByEmail(useremail).get();
		if(user.getPermission().equalsIgnoreCase("ROLE_ADMIN")) {
			if(chatRoomRepository.existsById(chatroomId)) {
				List<Message> messages = messageRepository.findAllByChatRoom_Id(chatroomId);
				return messages.stream().map(msg->MessageDTO.toDTO(msg)).collect(Collectors.toList());
			}
			return null;
		}
		else
			throw new IllegalArgumentException("권한이 없습니다.");
		
		

	}
}
