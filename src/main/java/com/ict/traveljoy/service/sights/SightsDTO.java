package com.ict.traveljoy.service.sights;


import com.ict.traveljoy.repository.region.Region;
import com.ict.traveljoy.repository.sights.Sights;

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
public class SightsDTO {
	private Long id;
	private Region region;
	private char isHasImage;
	private float entranceFee;
	private String sightName;
	private String descriptions;
	private String address;
	private float lat;
	private float lng;
	private Long totalReviewCount;
	private float averageReviewRate;
	
	public Sights toEntity() {
		return Sights.builder()
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
	
	public static SightsDTO toDto(Sights sights) {
		return SightsDTO.builder()
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
