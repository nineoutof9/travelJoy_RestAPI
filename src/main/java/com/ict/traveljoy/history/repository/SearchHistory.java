package com.ict.traveljoy.history.repository;

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
@Table(name="search_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder



public class SearchHistory {
	
	@Id
	@Column(name="search_history_id")
	@SequenceGenerator(name = "seq_search_history",sequenceName = "seq_search_history",allocationSize = 1,initialValue = 1)
	@GeneratedValue(generator = "seq_search_history",strategy = GenerationType.SEQUENCE)
	private long id;
	
	@Column(name="user_id")
	private long userId;
	
	@Column(name="search_word")
	private String searchWord;
	
	@Column(name="search_date")
	@ColumnDefault("SYSDATE")
	@CreationTimestamp
	private LocalDateTime searchDate;
	
	@Column(name="is_active")
	private char isActive;
	
	@Column(name="is_delete")
	private char isDelete;
	
	@Column(name="delete_date")
	@CreationTimestamp
    private LocalDateTime deleteDate;
}
