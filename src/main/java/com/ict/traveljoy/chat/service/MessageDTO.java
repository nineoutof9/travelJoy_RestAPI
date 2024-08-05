package com.ict.traveljoy.chat.service;

import java.time.LocalDateTime;

import com.ict.traveljoy.chat.repository.Message;
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
public class MessageDTO {

	private long id;
	private long chatRoomId;
	private long userId;
	private String messageContent;
	private LocalDateTime messageSendDate;
	private LocalDateTime messageReceiveDate;
	
	public Message toEnity() {
		return Message.builder()
				.id(id)
				.chatRoomId(chatRoomId)
				.userId(userId)
				.messageContent(messageContent)
				.messageSendDate(messageSendDate)
				.messageReceiveDate(messageReceiveDate)
				.build();
	}
	
	public MessageDTO toDTO(Message message) {
		return MessageDTO.builder()
				.id(id)
				.chatRoomId(chatRoomId)
				.userId(userId)
				.messageContent(messageContent)
				.messageSendDate(messageSendDate)
				.messageReceiveDate(messageReceiveDate)
				.build();
	}
}
