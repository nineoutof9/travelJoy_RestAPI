package com.ict.traveljoy.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ict.traveljoy.chat.service.EnterChatRoomDTO;


public interface EnterChatRoomRepository extends JpaRepository<EnterChatRoom, Long>{

	Boolean existsByUser_Id(Long userid);

	EnterChatRoom findByUser_Id(Long userid);


	int countByUser_Id(Long id);


	EnterChatRoom findByChatRoom_Id(Long id);


	
}