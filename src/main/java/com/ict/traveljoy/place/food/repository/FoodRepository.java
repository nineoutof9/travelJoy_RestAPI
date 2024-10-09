package com.ict.traveljoy.place.food.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    // 특정 지역의 음식 검색
    List<Food> findByRegion_Id(Long regionId);

    // 음식 이름으로 검색
    List<Food> findByFoodName(String foodName);
    List<Food> findByAddress(String address);
    List<Food> findByFoodNameAndAddress(String foodName, String address);

    // 특정 가격 이하의 음식 검색
    List<Food> findByAveragePriceLessThanEqual(float maxPrice);

    // 특정 리뷰 평점 이상의 음식 검색
    List<Food> findByAverageReviewRateGreaterThanEqual(float reviewRate);
    
    
    //lat, lng 기준 dist 거리 안에 있는것들 가져오기
    @Query(value = "SELECT * FROM FOOD WHERE 6371 * ACOS(" +
            "COS(:lat * 3.141592653589793 / 180) * COS(LAT * 3.141592653589793 / 180) * " +
            "COS(LNG * 3.141592653589793 / 180 - :lng * 3.141592653589793 / 180) + " +
            "SIN(:lat * 3.141592653589793 / 180) * SIN(LAT * 3.141592653589793 / 180)) <= :dist " +
            "ORDER BY 6371 * ACOS(" +
            "COS(:lat * 3.141592653589793 / 180) * COS(LAT * 3.141592653589793 / 180) * " +
            "COS(LNG * 3.141592653589793 / 180 - :lng * 3.141592653589793 / 180) + " +
            "SIN(:lat * 3.141592653589793 / 180) * SIN(LAT * 3.141592653589793 / 180)) ASC",
    nativeQuery = true)
	List<Food> findFoodsWithinDistance(
	     @Param("lat") double latitude, 
	     @Param("lng") double longitude, 
	     @Param("dist") double distance);
}
