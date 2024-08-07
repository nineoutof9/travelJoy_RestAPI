package com.ict.traveljoy.chat.service;

import java.time.LocalDateTime;

import com.ict.traveljoy.chat.repository.ChatRoom;
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
public class ChatRoomDTO {

	private long id;
	private LocalDateTime createDate;
	private LocalDateTime deleteDate;
	private boolean isActive;
	private boolean isDelete;
	
	public ChatRoom toEntity() {
		return ChatRoom.builder()
				.id(id)
				.createDate(createDate)
				.deleteDate(deleteDate)
				.isActive(isActive)
				.isDelete(isDelete)
				.build();
	}
	
	public static ChatRoomDTO toDTO(ChatRoom chatRoom) {
		return ChatRoomDTO.builder()
				.id(chatRoom.getId())
				.createDate(chatRoom.getCreateDate())
				.deleteDate(chatRoom.getDeleteDate())
				.isActive(chatRoom.getIsActive())
				.isDelete(chatRoom.getIsDelete())
				.build();
	}
	
	
}
