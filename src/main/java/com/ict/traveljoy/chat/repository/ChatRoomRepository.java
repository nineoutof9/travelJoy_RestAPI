package com.ict.traveljoy.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>{

//	List<ChatRoom> findByUserId(Long userId);
}
