package com.ict.traveljoy.repository.pushalarm;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.ict.traveljoy.repository.chat.ChatRoom;

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
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PushAlarm {

	@Id
	@SequenceGenerator(name = "seq_push_alarm",sequenceName = "seq_push_alarm",allocationSize = 1,initialValue = 1)
	@GeneratedValue(generator = "seq_push_alarm",strategy = GenerationType.SEQUENCE)
	private long id;
	
	@Column(length=50)
	private String title;
	
	@Column(length=2000)
	private String pushAlarmContent;
	
	@Column(nullable = false)
	@ColumnDefault("'T'")
	private char isActive;
	
	@Column(nullable = false)
	@ColumnDefault("'F'")
	private char isDelete;
	
	@Column
	@ColumnDefault("SYSDATE")
	@CreationTimestamp
	private LocalDateTime deleteDate;
	
	
}
