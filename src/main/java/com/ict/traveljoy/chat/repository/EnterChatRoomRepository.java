package com.ict.traveljoy.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface EnterChatRoomRepository extends JpaRepository<EnterChatRoom, Long>{

	EnterChatRoom existsByUser_Id(Long id);

	
}