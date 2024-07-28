package com.ict.traveljoy.repository.chat;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
	
	List<Message> findByUserId(Long userId);
	
	List<Message> findByChatRoomId(Long chatRoomId);
	
	
}
