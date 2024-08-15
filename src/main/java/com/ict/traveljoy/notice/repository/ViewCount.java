package com.ict.traveljoy.notice.repository;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.ict.traveljoy.users.repository.Users;

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
@Table(name="view_count")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewCount {

	@Id
	@Column(name="VIEW_COUNT_ID")
	@SequenceGenerator(name = "seq_view_count",sequenceName = "seq_view_count",allocationSize = 1,initialValue = 1)
	@GeneratedValue(generator = "seq_view_count",strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name="NOTICE_ID")
	private Notice notice;
	
	@Column(name="USER_ID")
	private Users user;
	
	@Column(name="VIEW_DATE")
	@ColumnDefault("SYSDATE")
	@CreationTimestamp
    private LocalDateTime viewDate; //가장 최근에 본 날짜에 따라 횟수 증가/증가X
	
	@Column(name="COUNT")
	@ColumnDefault("0")
	private Long count;
}
