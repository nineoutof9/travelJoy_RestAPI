package com.ict.traveljoy.service.hotels;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.traveljoy.repository.hotels.Hotels;
import com.ict.traveljoy.repository.hotels.HotelsRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class HotelsService {
	
	//리포지토리 주입
	@Autowired
	private HotelsRepository hotelsRepository;
	
	//호텔 전체 검색
	public List<HotelsDTO> findAllHotels(){
		return hotelsRepository.findAll().stream()
								.map(hotel->HotelsDTO.toDto(hotel))
								.collect(Collectors.toList());
	}
	
	public Optional<HotelsDTO> findHotelById(Long id){
		return hotelsRepository.findById(id)
								.map(hotel->HotelsDTO.toDto(hotel));
	}
	
	public HotelsDTO saveHotel(HotelsDTO hotelsDto) {
		if(hotelsDto.getHotelname()=="") {
			throw new IllegalArgumentException("숙소 이름이 비어있어요");
		}
		Hotels hotels= hotelsDto.toEntity();
		hotels = hotelsRepository.save(hotels);
		return HotelsDTO.toDto(hotels);
	}
	
	public void deleteHotel(Long id) {
		hotelsRepository.deleteById(id);
	}
	//특정 지역의 호텔 검색
	public List<HotelsDTO> findHotelsByRegionId(Long regionId) {
        return hotelsRepository.findByRegion_Id(regionId).stream()
            .map(hotel->HotelsDTO.toDto(hotel))
            .collect(Collectors.toList());
    }
	// 호텔 이름으로 검색
    public List<HotelsDTO> findHotelsByName(String hotelname) {
        return hotelsRepository.findByHotelname(hotelname).stream()
                .map(hotel -> HotelsDTO.toDto(hotel))
                .collect(Collectors.toList());
    }

    // 가격 범위로 검색
    public List<HotelsDTO> findHotelsByPriceRange(float minPrice, float maxPrice) {
        return hotelsRepository.findByAveragePrice(minPrice, maxPrice).stream()
                .map(hotel -> HotelsDTO.toDto(hotel))
                .collect(Collectors.toList());
    }

    // 리뷰 수가 일정 수 이상인 호텔 검색
    public List<HotelsDTO> findHotelsByReviewCount(Long reviewCount) {
        return hotelsRepository.findByTotalReview(reviewCount).stream()
                .map(hotel -> HotelsDTO.toDto(hotel))
                .collect(Collectors.toList());
    }

    // 특정 리뷰 평점 이상의 호텔 검색
    public List<HotelsDTO> findHotelsByReviewRate(float reviewRate) {
        return hotelsRepository.findByAverageReviewRate(reviewRate).stream()
                .map(hotel -> HotelsDTO.toDto(hotel))
                .collect(Collectors.toList());
    }
	
}
