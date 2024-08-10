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

	private Long id;
	private Long userId;
	private Long targetId;
	private Boolean isEvent;
	private Boolean isFood;
	private Boolean isSight;
	private Boolean isHotel;
	private Boolean isActive;
	private Boolean isDelete;
	private LocalDateTime createDate;
	private LocalDateTime deleteDate;
	
	public Favorite toEntity() {
		return Favorite.builder()
				.id(id)
				.userId(userId)
				.targetId(targetId)
				.isEvent(isEvent == true ? 1 : 0)
				.isFood(isFood == true ? 1 : 0)
				.isSight(isSight == true ? 1 : 0)
				.isHotel(isHotel == true ? 1 : 0)
				.isActive(isActive == true ? 1 : 0)
				.isDelete(isDelete == true ? 1 : 0)
				.createDate(createDate)
				.deleteDate(deleteDate)
				.build();
	}
	
	public static FavoriteDTO toDTO(Favorite favorite) {
		return FavoriteDTO.builder()
				.id(favorite.getId())
				.userId(favorite.getUserId())
				.targetId(favorite.getTargetId())
				.isEvent(favorite.getIsEvent() == 1 ? true : false)
				.isFood(favorite.getIsEvent() == 1 ? true : false)
				.isSight(favorite.getIsEvent() == 1 ? true : false)
				.isHotel(favorite.getIsEvent() == 1 ? true : false)
				.isActive(favorite.getIsEvent() == 1 ? true : false)
				.isDelete(favorite.getIsEvent() == 1 ? true : false)
				.createDate(favorite.getCreateDate())
				.deleteDate(favorite.getDeleteDate())
				.build();
	}
}
