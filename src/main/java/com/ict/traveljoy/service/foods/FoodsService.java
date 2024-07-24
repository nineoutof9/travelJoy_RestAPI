package com.ict.traveljoy.service.foods;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.traveljoy.repository.foods.Foods;
import com.ict.traveljoy.repository.foods.FoodsRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FoodsService {

    @Autowired
    private FoodsRepository foodsRepository;

    // 모든 음식 검색
    public List<FoodsDTO> findAllFoods() {
        return foodsRepository.findAll().stream()
                .map(foods -> FoodsDTO.toDto(foods))
                .collect(Collectors.toList());
    }

    // ID로 음식 검색
    public Optional<FoodsDTO> findFoodById(Long id) {
        return foodsRepository.findById(id)
                .map(foods -> FoodsDTO.toDto(foods));
    }

    // 음식 저장
    public FoodsDTO saveFood(FoodsDTO foodsDto) {
        // 데이터 유효성 검증
        if (foodsDto.getFoodName() == null) {
            throw new IllegalArgumentException("음식 이름이 비어있으면 안돼요");
        }

        Foods food = foodsDto.toEntity();
        food = foodsRepository.save(food);
        return FoodsDTO.toDto(food);
    }

    // ID로 음식 삭제
    public void deleteFood(Long id) {
        if (foodsRepository.existsById(id)) {
            foodsRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("주어진 번호의 음식을 찾을 수 없어요");
        }
    }

    // 특정 지역의 음식 검색
    public List<FoodsDTO> findFoodsByRegionId(Long regionId) {
        return foodsRepository.findByRegion_Id(regionId).stream()
                .map(foods -> FoodsDTO.toDto(foods))
                .collect(Collectors.toList());
    }

    // 음식 이름으로 검색
    public List<FoodsDTO> findFoodsByName(String foodName) {
        return foodsRepository.findByFoodName(foodName).stream()
                .map(foods -> FoodsDTO.toDto(foods))
                .collect(Collectors.toList());
    }

    // 특정 가격 이하의 음식 검색
    public List<FoodsDTO> findFoodsByPrice(float maxPrice) {
        return foodsRepository.findByAveragePrice(maxPrice).stream()
                .map(foods -> FoodsDTO.toDto(foods))
                .collect(Collectors.toList());
    }

    // 특정 리뷰 평점 이상의 음식 검색
    public List<FoodsDTO> findFoodsByReviewRate(float reviewRate) {
        return foodsRepository.findByAverageReviewRate(reviewRate).stream()
                .map(foods -> FoodsDTO.toDto(foods))
                .collect(Collectors.toList());
    }
}
