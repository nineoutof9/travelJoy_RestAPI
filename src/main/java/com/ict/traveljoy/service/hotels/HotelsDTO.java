package com.ict.traveljoy.service.hotels;

import com.ict.traveljoy.repository.hotels.Hotels;
import com.ict.traveljoy.repository.region.Region;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelsDTO {
	private Long id;
	private Region region;
	private char isHasImage;
	private float averagePrice;
	private String hotelname;
	private String descriptions;
	private String address;
	private float lat;
	private float lng;
	private Long totalReviewCount;
	private float averageReviewRate;
		
	public Hotels toEntity() {
		return Hotels.builder()
					 .id(id)
					 .region(region)
					 .isHasImage(isHasImage)
					 .averagePrice(averagePrice)
					 .hotelName(hotelname)
					 .descriptions(descriptions)
					 .address(address)
					 .lat(lat)
					 .lng(lng)
					 .totalReviewCount(totalReviewCount)
					 .averageReviewRate(averageReviewRate)
					 .build();
	}
	
	public static HotelsDTO toDto(Hotels hotels) {
		return HotelsDTO.builder()
						.id(hotels.getId())
						.region(hotels.getRegion())
						.isHasImage(hotels.getIsHasImage())
						.averagePrice(hotels.getAveragePrice())
						.hotelname(hotels.getHotelName())
						.descriptions(hotels.getDescriptions())
						.address(hotels.getAddress())
						.lat(hotels.getLat())
						.lng(hotels.getLng())
						.totalReviewCount(hotels.getTotalReviewCount())
						.averageReviewRate(hotels.getAverageReviewRate())
						.build();
	}

}
