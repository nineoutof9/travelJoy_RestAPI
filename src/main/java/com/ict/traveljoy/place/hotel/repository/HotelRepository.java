package com.ict.traveljoy.place.hotel.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    List<Hotel> findByHotelName(String hotelName);

    // 지역 이름과 날짜를 기준으로 호텔 검색
    List<Hotel> findByRegionNameContainingAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(
            String regionName, LocalDate checkInDate, LocalDate checkOutDate);
    
    
    @Query(value = "SELECT * FROM (" +
            "  SELECT id, name, lat, lng, " +
            "         (6371 * acos(cos(radians(:lat)) * cos(radians(lat)) * " +
            "                     cos(radians(lng) - radians(:lng)) + " +
            "                     sin(radians(:lat)) * sin(radians(lat)))) AS distance " +
            "  FROM your_table_name " +
            "  ORDER BY distance" +
            ") WHERE ROWNUM <= 5",
    nativeQuery = true)
    List<Hotel> findTop5ByDistance(@Param("lat") double latitude, @Param("lng") double longitude);
}
