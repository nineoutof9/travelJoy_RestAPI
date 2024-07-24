package com.ict.traveljoy.repository.members;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import com.ict.traveljoy.repository.converter.PermissionToNumberConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
@Table(name = "members")
@DynamicInsert
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
	
	@Id
	@SequenceGenerator(name = "seq_members",sequenceName = "seq_members",allocationSize = 1,initialValue = 1)
	@GeneratedValue(generator = "seq_members",strategy = GenerationType.SEQUENCE)
	private long id;
	
	@Column(length = 320, unique = true, nullable = true)
	private String email;
	
	@Column(length = 64,nullable = true)
	private String password;
	
	@Column(length = 30)
	private String name;
	
	@Column(length = 30,nullable = true)
	private String nickname;
	
	@Column
	private java.sql.Date birthDate;
	
	@Column(nullable = false)
	@ColumnDefault("SYSDATE")
	@CreationTimestamp
	private LocalDateTime signInDate;
	
	@Column
	private boolean gender;
	
	@Column(nullable = true)
	private boolean isKakao;
	
	@Column(nullable = true)
	private boolean isGoogle;
	
	@Column(nullable = true)
	private boolean isNaver;
	
	@Convert(converter = PermissionToNumberConverter.class)
	@Column(name = "permission")
	private String permission;
	
	@Column
	private boolean handicap;
	
	@Column(nullable = true)
	private boolean handicapAllow;
	
	@Column
	private boolean allergy;
	
	@Column(nullable = true)
	private boolean allergyAllow;
	
	@Column
	private boolean interest;
	
	@Column(nullable = true)
	private boolean interestAllow;
	
	@Column
	private long reported;
	
	@Column(nullable = true)
	private boolean isDeleteId;
	
	@Column
	private LocalDateTime deleteIdDate;
	
	@Column(nullable = true)
	private boolean state;
	
	@Column
	@ColumnDefault("SYSDATE")
	@CreationTimestamp
	private LocalDateTime updateDate;
	
}
