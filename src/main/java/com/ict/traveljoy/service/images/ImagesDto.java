package com.ict.traveljoy.service.images;

import com.ict.traveljoy.repository.images.Images;

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
public class ImagesDto {
	private long imageId;
	private String imageUrl;
	
	public Images toEntity() {
		return Images.builder()
				.imageId(imageId)
				.imageUrl(imageUrl)
				.build();
	}
	
	public ImagesDto toDto(Images img) {
		return ImagesDto.builder()
				.imageId(img.getImageId())
				.imageUrl(img.getImageUrl())
				.build();
	}
}
