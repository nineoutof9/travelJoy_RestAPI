package com.ict.traveljoy.favorite.service;

import java.time.LocalDateTime;

import com.ict.traveljoy.favorite.repository.Favorite;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteDTO {

	private long id;
	private long userId;
	private long targetId;
	private boolean isEvent;
	private boolean isFood;
	private boolean isSight;
	private boolean isHotel;
	private boolean isActive;
	private boolean isDelete;
	private LocalDateTime createDate;
	private LocalDateTime deleteDate;
	
	public Favorite toEntity() {
		return Favorite.builder()
				.id(id)
				.userId(userId)
				.targetId(targetId)
				.isEvent(isEvent==true?'T':'F')
				.isFood(isFood==true?'T':'F')
				.isSight(isSight==true?'T':'F')
				.isHotel(isHotel==true?'T':'F')
				.isActive(isActive==true?'T':'F')
				.isDelete(isDelete==true?'T':'F')
				.createDate(createDate)
				.deleteDate(deleteDate)
				.build();
	}
	
	public static FavoriteDTO toDTO(Favorite favorite) {
		return FavoriteDTO.builder()
				.id(favorite.getId())
				.userId(favorite.getUserId())
				.targetId(favorite.getTargetId())
				.isEvent(favorite.getIsEvent()=='T'?true:false)
				.isFood(favorite.getIsEvent()=='T'?true:false)
				.isSight(favorite.getIsEvent()=='T'?true:false)
				.isHotel(favorite.getIsEvent()=='T'?true:false)
				.isActive(favorite.getIsEvent()=='T'?true:false)
				.isDelete(favorite.getIsEvent()=='T'?true:false)
				.createDate(favorite.getCreateDate())
				.deleteDate(favorite.getDeleteDate())
				.build();
	}
}
