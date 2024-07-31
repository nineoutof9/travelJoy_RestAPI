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
public class ImageDTO {
	
	private long id;
	private String imageUrl;
	
	public Image toImage() {
		return Image.builder()
				.id(id)
				.imageUrl(imageUrl)
				.build();
	}
	
	public static ImageDTO toDTO(Image image) {
		return ImageDTO.builder()
				.id(image.getId())
				.imageUrl(image.getImageUrl())
				.build();
	}

}
