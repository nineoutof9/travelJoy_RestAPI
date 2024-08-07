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
				.isEvent(isEvent)
				.isFood(isFood)
				.isSight(isSight)
				.isHotel(isHotel)
				.isActive(isActive)
				.isDelete(isDelete)
				.createDate(createDate)
				.deleteDate(deleteDate)
				.build();
	}
	
	public static FavoriteDTO toDTO(Favorite favorite) {
		return FavoriteDTO.builder()
				.id(favorite.getId())
				.userId(favorite.getUserId())
				.targetId(favorite.getTargetId())
				.isEvent(favorite.getIsEvent())
				.isFood(favorite.getIsEvent())
				.isSight(favorite.getIsEvent())
				.isHotel(favorite.getIsEvent())
				.isActive(favorite.getIsEvent())
				.isDelete(favorite.getIsEvent())
				.createDate(favorite.getCreateDate())
				.deleteDate(favorite.getDeleteDate())
				.build();
	}
}
