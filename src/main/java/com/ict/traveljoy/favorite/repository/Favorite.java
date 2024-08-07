package com.ict.traveljoy.favorite.repository;

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
@Table(name="favorite")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Favorite {

	@Id
	@Column(name="favorite_id")
	@SequenceGenerator(name = "seq_favorite",sequenceName = "seq_favorite",allocationSize = 1,initialValue = 1)
	@GeneratedValue(generator = "seq_favorite",strategy = GenerationType.SEQUENCE)
	private long id;
	
	@Column(name="user_id")
	private long userId;
	
	@Column(name="target_id")
	private long targetId;
	
	@Column(name="is_event")
	@ColumnDefault("'F'")
	private boolean isEvent;
	
	@Column(name="is_food")
	@ColumnDefault("'F'")
	private boolean isFood;
	
	@Column(name="is_sight")
	@ColumnDefault("'F'")
	private boolean isSight;
	
	@Column(name="is_hotel")
	@ColumnDefault("'F'")
	private boolean isHotel;
	
	@Column(name="is_active",nullable = false)
	@ColumnDefault("'T'")
	private boolean isActive;
	
	@Column(name="is_delete",nullable = false)
	@ColumnDefault("'F'")
	private boolean isDelete;
	
	@Column(name="create_date",nullable = false)
	@ColumnDefault("SYSDATE")
	@CreationTimestamp
    private LocalDateTime createDate;
	
	@Column(name="delete_date")
	@ColumnDefault("SYSDATE")
	@CreationTimestamp
    private LocalDateTime deleteDate;

	
}
