package com.ict.traveljoy.notice.repository;

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
@Table(name = "notice")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_notice")
    @SequenceGenerator(name = "seq_notice", sequenceName = "seq_notice", allocationSize = 1)
    @Column(nullable = false, name = "NOTICE_ID")
	private long id;
	
	@ColumnDefault("SYSDATE")
	@CreationTimestamp
	private LocalDateTime date;
	
	@Column(nullable = false,name="NOTICE_TITLE")
	private String title; 
	
	@Column(nullable = false,name="NOTICE_CONTENT",length = 2000)
	private String content;
	
	@Column(nullable = false)
	private String writer;
	
	@ColumnDefault("'F'")
	private boolean isDelete;
	
	@ColumnDefault("'T'")
	private boolean isActive;

}
