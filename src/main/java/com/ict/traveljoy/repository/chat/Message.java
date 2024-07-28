package com.ict.traveljoy.repository.chat;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

	@Id
	@Column(name="message_id")
	@SequenceGenerator(name = "seq_message",sequenceName = "seq_message",allocationSize = 1,initialValue = 1)
	@GeneratedValue(generator = "seq_message",strategy = GenerationType.SEQUENCE)
	private long id;
	
	@Column(name="chat_room_id")
	private long chatRoomId;
	
	@Column(name="user_id")
	private long userId;
	
	@Column(name="message_content",length=2000)
	private String messageContent;
	
	//보낸시간이랑 받은시간 같지 않은지?
	@Column(name="message_send_date")
	@ColumnDefault("SYSDATE")
	@CreationTimestamp
	private LocalDateTime messageSendDate;
	
	@Column(name="message_receive_date")
	@ColumnDefault("SYSDATE")
	@CreationTimestamp
	private LocalDateTime messageReceiveDate;
	
	
	private char isActive;
	
	private char isDelete;
	
	//메세지 삭제 기능 넣을것인지?
	@Column(name="delete_date")
	@CreationTimestamp
	private LocalDateTime deleteDate;
}
