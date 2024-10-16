package com.ict.traveljoy.place.hotel.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HotelRepository extends JpaRepository<Hotel, Long> {


    //List<Hotel> findByRegionId(Long region_Id);

    List<Hotel> findByHotelName(String hotelName);

    //List<Hotel> findByAveragePriceBetween(Float minPrice, Float maxPrice);

    //List<Hotel> findByTotalReviewCountGreaterThanEqual(Long reviewCount);

    //List<Hotel> findByAverageReviewRateGreaterThanEqual(Float reviewRate);

    // 지역 이름과 날짜를 기준으로 호텔 검색
    List<Hotel> findByRegionNameContainingAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(
            String regionName, LocalDate checkInDate, LocalDate checkOutDate);

    @Query(value = "SELECT hotel_id, hotel_name, average_price, is_has_image, lat, lng, region_id, average_review_rate, check_in_date, check_out_date FROM (" +
            "  SELECT hotel_id, hotel_name, average_price, is_has_image, lat, lng, region_id, average_review_rate, check_in_date, check_out_date, " +
            "         (6371 * acos(cos((:lat * (3.14159265359 / 180))) * cos(lat * (3.14159265359 / 180)) * " +
            "                     cos(lng * (3.14159265359 / 180) - (:lng * (3.14159265359 / 180))) + " +
            "                     sin((:lat * (3.14159265359 / 180))) * sin(lat * (3.14159265359 / 180)))) AS distance " +
            "  FROM HOTEL " +
            "  ORDER BY distance" +
            ") WHERE ROWNUM <= 5",
    nativeQuery = true)
    List<Hotel> findTop5ByDistance(@Param("lat") double latitude, @Param("lng") double longitude);
  
    //lat, lng 기준 dist 거리 안에 있는것들 가져오기
    @Query(value = "SELECT * FROM HOTEL WHERE 6371 * ACOS(" +
            "COS(:lat * 3.141592653589793 / 180) * COS(LAT * 3.141592653589793 / 180) * " +
            "COS(LNG * 3.141592653589793 / 180 - :lng * 3.141592653589793 / 180) + " +
            "SIN(:lat * 3.141592653589793 / 180) * SIN(LAT * 3.141592653589793 / 180)) <= :dist " +
            "ORDER BY 6371 * ACOS(" +
            "COS(:lat * 3.141592653589793 / 180) * COS(LAT * 3.141592653589793 / 180) * " +
            "COS(LNG * 3.141592653589793 / 180 - :lng * 3.141592653589793 / 180) + " +
            "SIN(:lat * 3.141592653589793 / 180) * SIN(LAT * 3.141592653589793 / 180)) ASC", 
    nativeQuery = true)
List<Hotel> findHotelsWithinDistance(@Param("lat") double latitude, @Param("lng") double longitude, @Param("dist") double distance);


}
