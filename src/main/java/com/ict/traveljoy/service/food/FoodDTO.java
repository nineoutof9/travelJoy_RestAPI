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
    private boolean isHasImage;
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

    public static FoodDTO toDto(Food food) {
        return FoodDTO.builder()
                .id(food.getId())
                .region(food.getRegion())
                .foodType(food.getFoodType())
                .isHasImage(food.getIsHasImage())
                .averagePrice(food.getAveragePrice())
                .foodName(food.getFoodName())
                .descriptions(food.getDescriptions())
                .address(food.getAddress())
                .lat(food.getLat())
                .lng(food.getLng())
                .totalReviewCount(food.getTotalReviewCount())
                .averageReviewRate(food.getAverageReviewRate())
                .build();
    }
}
