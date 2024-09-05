package com.ict.traveljoy.place.food.service;

import com.ict.traveljoy.place.food.repository.Food;
import com.ict.traveljoy.place.region.repository.Region;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodDTO {
    private Long id;
    private Region region;
    private Boolean isHasImage;  // Boolean 타입으로 수정
    private Float averagePrice;
    private String foodName;
    private String address;
    private Float lat;
    private Float lng;
    private Float averageReviewRate;
    private List<String> imageUrls;
    private String tel;
    private String workingTime;

    // Entity로 변환하는 메서드
    public Food toEntity() {
        // Region이 null이면 예외를 던지거나 적절한 기본값 설정
        if (region == null || region.getName() == null || region.getName().isEmpty()) {
            throw new IllegalArgumentException("Region 정보가 없거나 올바르지 않습니다.");
        }

        return Food.builder()
                .id(id)
                .region(region)  // 이미 Region이 설정되어 있는 경우
                .isHasImage(isHasImage != null && isHasImage ? 1 : 0)  // Boolean을 Integer로 변환
                .averagePrice(averagePrice)
                .foodName(foodName)
                .address(address)
                .lat(lat)
                .lng(lng)
                .averageReviewRate(averageReviewRate)
                .imageUrls(imageUrls)  // 추가된 필드
                .tel(tel)
                .workingTime(workingTime)
                .build();
    }

    public static FoodDTO toDto(Food food) {
        return FoodDTO.builder()
                .id(food.getId())
                .region(food.getRegion())
                .isHasImage(food.getIsHasImage() == 1)  // Integer를 Boolean으로 변환
                .averagePrice(food.getAveragePrice())
                .foodName(food.getFoodName())
                .address(food.getAddress())
                .lat(food.getLat())
                .lng(food.getLng())
                .averageReviewRate(food.getAverageReviewRate())
                .imageUrls(food.getImageUrls())  // 추가된 필드
                .tel(food.getTel())
                .workingTime(food.getWorkingTime())
                .build();
    }
}
