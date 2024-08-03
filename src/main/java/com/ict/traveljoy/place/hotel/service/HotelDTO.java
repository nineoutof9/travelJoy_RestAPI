package com.ict.traveljoy.place.hotel.service;

import com.ict.traveljoy.place.hotel.repository.Hotel;
import com.ict.traveljoy.place.region.repository.Region;

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
public class HotelDTO {
	private Long id;
	private Region region;
	private boolean isHasImage;
	private float averagePrice;
	private String hotelname;
	private String descriptions;
	private String address;
	private float lat;
	private float lng;
	private Long totalReviewCount;
	private float averageReviewRate;
		
	public Hotel toEntity() {
		return Hotel.builder()
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
	
	public static HotelDTO toDto(Hotel hotels) {
		return HotelDTO.builder()
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
