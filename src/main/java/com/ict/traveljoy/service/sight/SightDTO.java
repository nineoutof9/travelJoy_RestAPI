package com.ict.traveljoy.service.sight;


import com.ict.traveljoy.repository.region.Region;
import com.ict.traveljoy.repository.sight.Sight;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SightDTO {
	private Long id;
	private Region region;
	private boolean isHasImage;
	private float entranceFee;
	private String sightName;
	private String descriptions;
	private String address;
	private float lat;
	private float lng;
	private Long totalReviewCount;
	private float averageReviewRate;
	
	public Sight toEntity() {
		return Sight.builder()
					 .id(id)
					 .region(region)
					 .isHasImage(isHasImage)
					 .entranceFee(entranceFee)
					 .sightName(sightName)
					 .descriptions(descriptions)
					 .address(address)
					 .lat(lat)
					 .lng(lng)
					 .totalReviewCount(totalReviewCount)
					 .averageReviewRate(averageReviewRate)
					 .build();
	}
	
	public static SightDTO toDto(Sight sights) {
		return SightDTO.builder()
						.id(sights.getId())
						.region(sights.getRegion())
						.isHasImage(sights.getIsHasImage())
						.entranceFee(sights.getEntranceFee())
						.sightName(sights.getSightName())
						.descriptions(sights.getDescriptions())
						.address(sights.getAddress())
						.lat(sights.getLat())
						.lng(sights.getLng())
						.totalReviewCount(sights.getTotalReviewCount())
						.averageReviewRate(sights.getAverageReviewRate())
						.build();
	}

}
