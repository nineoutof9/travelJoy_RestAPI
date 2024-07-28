package com.ict.traveljoy.service.chat;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.repository.chat.ChatRoomRepository;
import com.ict.traveljoy.repository.favorite.FavoriteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
	
	private final ChatRoomRepository chatRoomRepository;
	private final ObjectMapper objectMapper;

}
