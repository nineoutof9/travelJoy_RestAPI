package com.ict.traveljoy.repository.chat;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.ict.traveljoy.repository.report.Report;

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
@Table(name="chat_room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {
	
	@Id
	@Column(name="chat_room_id")
	@SequenceGenerator(name = "seq_chatroom",sequenceName = "seq_chatroom",allocationSize = 1,initialValue = 1)
	@GeneratedValue(generator = "seq_chatroom",strategy = GenerationType.SEQUENCE)
	private long id;
	
	@Column(name="create_date",nullable = false)
	@ColumnDefault("SYSDATE")
	@CreationTimestamp
    private LocalDateTime createDate;
	
	@Column(name="delete_date")
	@ColumnDefault("SYSDATE")
	@CreationTimestamp
    private LocalDateTime deleteDate;
	
	@Column(name="is_active")
	@ColumnDefault("y")
	private char isActive;
	
	@Column(name="is_delete")
	@ColumnDefault("n")
	private char isDelete;
	
	
}
