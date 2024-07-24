package com.ict.traveljoy.repository.hotels;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ict.traveljoy.service.hotels.HotelsDTO;

public interface HotelsRepository extends JpaRepository<Hotels, Long>{

	List<Hotels> findByRegion_Id(Long regionId);
	
	List<Hotels> findByHotelname(String hotelname);
    
    List<Hotels> findByAveragePrice(float minPrice, float maxPrice);
    
    List<Hotels> findByTotalReview(Long reviewCount);
    
    List<Hotels> findByAverageReviewRate(float reviewRate);
	
}
