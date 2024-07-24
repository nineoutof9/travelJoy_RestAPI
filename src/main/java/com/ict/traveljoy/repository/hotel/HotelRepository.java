package com.ict.traveljoy.repository.hotel;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ict.traveljoy.service.hotel.HotelDTO;

public interface HotelRepository extends JpaRepository<Hotel, Long>{

	List<Hotel> findByRegion_Id(Long regionId);
	
	List<Hotel> findByHotelname(String hotelname);
    
    List<Hotel> findByAveragePrice(float minPrice, float maxPrice);
    
    List<Hotel> findByTotalReview(Long reviewCount);
    
    List<Hotel> findByAverageReviewRate(float reviewRate);
	
}
