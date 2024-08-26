package com.ict.traveljoy.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface EnterChatRoomRepository extends JpaRepository<EnterChatRoom, Long>{

	Boolean existsByUser_Id(Long userid);

	EnterChatRoom findByUser_Id(Long userid);

	
}