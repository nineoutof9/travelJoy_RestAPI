package com.ict.traveljoy.place.food.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.traveljoy.place.food.repository.Food;
import com.ict.traveljoy.place.food.repository.FoodRepository;
import com.ict.traveljoy.place.region.repository.Region;
import com.ict.traveljoy.place.region.repository.RegionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;
    
    @Autowired
    private RegionRepository regionRepository;

    // 모든 음식 검색
    public List<FoodDTO> findAllFoods() {
        return foodRepository.findAll().stream()
                .map(FoodDTO::toDto)
                .collect(Collectors.toList());
    }

    // ID로 음식 검색
    public Optional<FoodDTO> findFoodById(Long id) {
        return foodRepository.findById(id)
                .map(FoodDTO::toDto);
    }

    // 음식 저장
    @Transactional
    public FoodDTO saveFood(FoodDTO foodDto) {
        if (foodDto.getFoodName() == null || foodDto.getFoodName().isEmpty()) {
            throw new IllegalArgumentException("음식 이름이 비어있어요");
        }

        // Region 정보 확인 및 처리
        Region region = foodDto.getRegion();
        if (region == null || region.getName() == null || region.getName().isEmpty()) {
            throw new IllegalArgumentException("Region 정보가 없거나 올바르지 않습니다.");
        }

        // 동일한 이름을 가진 Region 중 첫 번째 Region 사용
        List<Region> regions = regionRepository.findByName(region.getName());
        if (regions.isEmpty()) {
            System.out.println("새로운 Region 저장: " + region.getName());
            region = regionRepository.save(region);  // 새로운 Region 저장
        } else {
            region = regions.get(0);  // 첫 번째 Region 사용
        }

        // Food Entity 생성 및 저장
        Food food = foodDto.toEntity();
        food.setRegion(region);  // 저장된 Region 설정

        // 이미지 URL이 있으면 is_has_image를 1로 설정
        if (foodDto.getImageUrls() != null && !foodDto.getImageUrls().isEmpty()) {
            food.setIsHasImage(1);  // 이미지가 있으면 is_has_image를 1로 설정
        } else {
            food.setIsHasImage(0);  // 이미지가 없으면 is_has_image를 0으로 설정
        }

        // Print 디버깅: 저장할 Food 정보
        System.out.println("Saving food: " + food.getFoodName());
        System.out.println("Food region: " + food.getRegion().getName());

        food = foodRepository.save(food);

        // Print 디버깅: 저장된 음식 정보 출력
        System.out.println("Saved food with ID: " + food.getId());

        return FoodDTO.toDto(food);
    }
    
    // 음식 수정
    @Transactional
    public FoodDTO updateFood(Long id, FoodDTO foodDto) {
        Optional<Food> foodOpt = foodRepository.findById(id);

        if (foodOpt.isPresent()) {
            Food food = foodOpt.get();

            // Region의 name 필드에 country 값을 업데이트하는 로직 추가
            Region region = foodDto.getRegion();
            if (region == null || region.getName() == null || region.getName().isEmpty()) {
                throw new IllegalArgumentException("Region 정보가 없거나 올바르지 않습니다.");
            }

            // 동일한 이름을 가진 Region 중 첫 번째 Region 사용
            List<Region> regions = regionRepository.findByName(region.getName());
            if (regions.isEmpty()) {
                System.out.println("새로운 Region 저장: " + region.getName());
                region = regionRepository.save(region);  // 새로운 Region 저장
            } else {
                region = regions.get(0);  // 첫 번째 Region 사용
            }

            // 음식 정보 업데이트
            food.setFoodName(foodDto.getFoodName());
            food.setRegion(region);  // Region 업데이트
            food.setAddress(foodDto.getAddress());
            food.setAveragePrice(foodDto.getAveragePrice());
            food.setAverageReviewRate(foodDto.getAverageReviewRate());
            food.setLat(foodDto.getLat());
            food.setLng(foodDto.getLng());

            // 이미지 URL이 있으면 is_has_image를 1로 설정
            if (foodDto.getImageUrls() != null && !foodDto.getImageUrls().isEmpty()) {
                food.setIsHasImage(1);  // 이미지가 있으면 is_has_image를 1로 설정
            } else {
                food.setIsHasImage(0);  // 이미지가 없으면 is_has_image를 0으로 설정
            }

            Food updatedFood = foodRepository.save(food);

            // Print 디버깅: 업데이트된 음식 정보 출력
            System.out.println("Updated food with ID: " + updatedFood.getId());

            return FoodDTO.toDto(updatedFood);
        } else {
            throw new IllegalArgumentException("주어진 번호의 음식을 찾을 수 없어요");
        }
    }

    // ID로 음식 삭제
    @Transactional
    public void deleteFood(Long id) {
        if (foodRepository.existsById(id)) {
            System.out.println("Deleting food with ID: " + id);
            foodRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("주어진 번호의 음식을 찾을 수 없어요");
        }
    }

    // 특정 지역의 음식 검색
    public List<FoodDTO> findFoodsByRegionId(Long regionId) {
        return foodRepository.findByRegion_Id(regionId).stream()
                .map(FoodDTO::toDto)
                .collect(Collectors.toList());
    }

    // 음식 이름으로 검색
    public List<FoodDTO> findFoodsByName(String foodName) {
        return foodRepository.findByFoodName(foodName).stream()
                .map(FoodDTO::toDto)
                .collect(Collectors.toList());
    }

    // 특정 가격 이하의 음식 검색
    public List<FoodDTO> findFoodsByPrice(float maxPrice) {
        return foodRepository.findByAveragePriceLessThanEqual(maxPrice).stream()
                .map(FoodDTO::toDto)
                .collect(Collectors.toList());
    }

    // 특정 리뷰 평점 이상의 음식 검색
    public List<FoodDTO> findFoodsByReviewRate(float reviewRate) {
        return foodRepository.findByAverageReviewRateGreaterThanEqual(reviewRate).stream()
                .map(FoodDTO::toDto)
                .collect(Collectors.toList());
    }
}
