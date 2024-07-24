package com.ict.traveljoy.service.food;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.traveljoy.repository.food.Food;
import com.ict.traveljoy.repository.food.FoodRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FoodService {

    @Autowired
    private FoodRepository foodsRepository;

    // 모든 음식 검색
    public List<FoodDTO> findAllFoods() {
        return foodsRepository.findAll().stream()
                .map(foods -> FoodDTO.toDto(foods))
                .collect(Collectors.toList());
    }

    // ID로 음식 검색
    public Optional<FoodDTO> findFoodById(Long id) {
        return foodsRepository.findById(id)
                .map(foods -> FoodDTO.toDto(foods));
    }

    // 음식 저장
    public FoodDTO saveFood(FoodDTO foodsDto) {
        // 데이터 유효성 검증
        if (foodsDto.getFoodName() == null) {
            throw new IllegalArgumentException("음식 이름이 비어있으면 안돼요");
        }

        Food food = foodsDto.toEntity();
        food = foodsRepository.save(food);
        return FoodDTO.toDto(food);
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
    public List<FoodDTO> findFoodsByRegionId(Long regionId) {
        return foodsRepository.findByRegion_Id(regionId).stream()
                .map(foods -> FoodDTO.toDto(foods))
                .collect(Collectors.toList());
    }

    // 음식 이름으로 검색
    public List<FoodDTO> findFoodsByName(String foodName) {
        return foodsRepository.findByFoodName(foodName).stream()
                .map(foods -> FoodDTO.toDto(foods))
                .collect(Collectors.toList());
    }

    // 특정 가격 이하의 음식 검색
    public List<FoodDTO> findFoodsByPrice(float maxPrice) {
        return foodsRepository.findByAveragePrice(maxPrice).stream()
                .map(foods -> FoodDTO.toDto(foods))
                .collect(Collectors.toList());
    }

    // 특정 리뷰 평점 이상의 음식 검색
    public List<FoodDTO> findFoodsByReviewRate(float reviewRate) {
        return foodsRepository.findByAverageReviewRate(reviewRate).stream()
                .map(foods -> FoodDTO.toDto(foods))
                .collect(Collectors.toList());
    }
}
