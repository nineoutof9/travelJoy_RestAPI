package com.ict.traveljoy.service.food;

import com.ict.traveljoy.repository.food.Food;
import com.ict.traveljoy.repository.region.Region;

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
public class FoodDTO {
	private Long id;
	private Region region;
	private String foodType;
	private char isHasImage;
	private float averagePrice;
	private String foodName;
	private String descriptions;
	private String address;
	private float lat;
	private float lng;
	private Long totalReviewCount;
	private float averageReviewRate;
		
		public Food toEntity() {
			return Food.builder()
						.id(id)
						.region(region)
						.foodType(foodType)
						.isHasImage(isHasImage)
						.averagePrice(averagePrice)
						.foodName(foodName)
						.descriptions(descriptions)
						.address(address)
						.lat(lat)
						.lng(lng)
						.totalReviewCount(totalReviewCount)
						.averageReviewRate(averageReviewRate)
						.build();
		}
		
		public static FoodDTO toDto(Food foods) {
			return FoodDTO.builder()
							.id(foods.getId())
							.region(foods.getRegion())
							.foodType(foods.getFoodType())
							.isHasImage(foods.getIsHasImage())
							.averagePrice(foods.getAveragePrice())
							.foodName(foods.getFoodName())
							.descriptions(foods.getDescriptions())
							.address(foods.getAddress())
							.lat(foods.getLat())
							.lng(foods.getLng())
							.totalReviewCount(foods.getTotalReviewCount())
							.averageReviewRate(foods.getAverageReviewRate())
							.build();
		}
}
