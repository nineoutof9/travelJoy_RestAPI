package com.ict.traveljoy.service.chat;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.repository.chat.EnterChatRoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnterChatRoomService {
	
	private final EnterChatRoomRepository enterChatRoomRepository;
	private final ObjectMapper objectMapper;

}
