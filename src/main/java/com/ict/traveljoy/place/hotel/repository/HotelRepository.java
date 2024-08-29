package com.ict.traveljoy.place.hotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    List<Hotel> findByRegionId(Long region_Id);

    List<Hotel> findByHotelName(String hotelName);

    List<Hotel> findByAveragePriceBetween(Float minPrice, Float maxPrice);

    List<Hotel> findByTotalReviewCountGreaterThanEqual(Long reviewCount);

    List<Hotel> findByAverageReviewRateGreaterThanEqual(Float reviewRate);
}
