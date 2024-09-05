package com.ict.traveljoy.chat.service;

import java.time.LocalDateTime;

import com.ict.traveljoy.chat.repository.ChatRoom;
import com.ict.traveljoy.chat.repository.EnterChatRoom;
import com.ict.traveljoy.users.repository.Users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnterChatRoomDTO {

	private long id;
	private ChatRoom chatRoom;
	private Users user;
	
	public EnterChatRoom toEntity() {
		return EnterChatRoom.builder()
				.id(id)
				.chatRoom(chatRoom)
				.user(user)
				.build();
	}
	
	public static EnterChatRoomDTO toDTO(EnterChatRoom enterChatRoom) {
		return EnterChatRoomDTO.builder()
				.id(enterChatRoom.getId())
				.chatRoom(enterChatRoom.getChatRoom())
				.user(enterChatRoom.getUser())
				.build();
	}
}
