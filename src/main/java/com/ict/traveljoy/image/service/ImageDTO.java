package com.ict.traveljoy.image.service;


import java.time.LocalDateTime;

import com.ict.traveljoy.image.repository.Image;

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
public class ImageDTO {
	
	private long id;
	private String imageUrl;
	private LocalDateTime saveDate;
	private boolean isActive;
	private boolean isDelete;
	private LocalDateTime deleteDate;
	
	public Image toImage() {
		return Image.builder()
				.id(id)
				.imageUrl(imageUrl)
				.saveDate(saveDate)
				.isActive(isActive)
				.isDelete(isDelete)
				.deleteDate(deleteDate)
				.build();
	}
	
	public static ImageDTO toDTO(Image image) {
		return ImageDTO.builder()
				.id(image.getId())
				.imageUrl(image.getImageUrl())
				.saveDate(image.getSaveDate())
				.isActive(image.getIsActive())
				.isDelete(image.getIsDelete())
				.deleteDate(image.getDeleteDate())
				.build();
	}

}
