package com.ict.traveljoy.repository.notice;

import java.time.LocalDateTime;

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
@Table(name = "notice")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notice_seq")
    @SequenceGenerator(name = "notice_seq", sequenceName = "notice_seq", allocationSize = 1)
    @Column(nullable = false, name = "NOTICE_ID")
	private long id;
	
	@Column(nullable = false,name = "NOTICE_DATE")
	private LocalDateTime date;
	
	@Column(nullable = false,name="NOTICE_TITLE")
	private String title; 
	
	@Column(nullable = false,name="NOTICE_CONTENT")
	private String content;
	
	@Column(nullable = false)
	private String writer;
	
	@Column(nullable = false,name = "IS_DELETE")
	private boolean isDelete;
	
	@Column(nullable = false,name = "IS_ACTIVE")
	private boolean isActive;

}
