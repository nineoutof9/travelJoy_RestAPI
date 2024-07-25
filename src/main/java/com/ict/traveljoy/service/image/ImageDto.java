package com.ict.traveljoy.service.image;

import com.ict.traveljoy.repository.image.Image;

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
public class ImageDto {
	private long imageId;
	private String imageUrl;
	
	public Image toEntity() {
		return Image.builder()
				.imageId(imageId)
				.imageUrl(imageUrl)
				.build();
	}
	
	public ImageDto toDto(Image img) {
		return ImageDto.builder()
				.imageId(img.getImageId())
				.imageUrl(img.getImageUrl())
				.build();
	}
}
