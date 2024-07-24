package com.ict.traveljoy.service.hotel;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.traveljoy.repository.hotel.Hotel;
import com.ict.traveljoy.repository.hotel.HotelRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class HotelService {
	
	//리포지토리 주입
	@Autowired
	private HotelRepository hotelsRepository;
	
	//호텔 전체 검색
	public List<HotelDTO> findAllHotels(){
		return hotelsRepository.findAll().stream()
								.map(hotel->HotelDTO.toDto(hotel))
								.collect(Collectors.toList());
	}
	
	public Optional<HotelDTO> findHotelById(Long id){
		return hotelsRepository.findById(id)
								.map(hotel->HotelDTO.toDto(hotel));
	}
	
	public HotelDTO saveHotel(HotelDTO hotelsDto) {
		if(hotelsDto.getHotelname()=="") {
			throw new IllegalArgumentException("숙소 이름이 비어있어요");
		}
		Hotel hotels= hotelsDto.toEntity();
		hotels = hotelsRepository.save(hotels);
		return HotelDTO.toDto(hotels);
	}
	
	public void deleteHotel(Long id) {
		hotelsRepository.deleteById(id);
	}
	//특정 지역의 호텔 검색
	public List<HotelDTO> findHotelsByRegionId(Long regionId) {
        return hotelsRepository.findByRegion_Id(regionId).stream()
            .map(hotel->HotelDTO.toDto(hotel))
            .collect(Collectors.toList());
    }
	// 호텔 이름으로 검색
    public List<HotelDTO> findHotelsByName(String hotelname) {
        return hotelsRepository.findByHotelname(hotelname).stream()
                .map(hotel -> HotelDTO.toDto(hotel))
                .collect(Collectors.toList());
    }

    // 가격 범위로 검색
    public List<HotelDTO> findHotelsByPriceRange(float minPrice, float maxPrice) {
        return hotelsRepository.findByAveragePrice(minPrice, maxPrice).stream()
                .map(hotel -> HotelDTO.toDto(hotel))
                .collect(Collectors.toList());
    }

    // 리뷰 수가 일정 수 이상인 호텔 검색
    public List<HotelDTO> findHotelsByReviewCount(Long reviewCount) {
        return hotelsRepository.findByTotalReview(reviewCount).stream()
                .map(hotel -> HotelDTO.toDto(hotel))
                .collect(Collectors.toList());
    }

    // 특정 리뷰 평점 이상의 호텔 검색
    public List<HotelDTO> findHotelsByReviewRate(float reviewRate) {
        return hotelsRepository.findByAverageReviewRate(reviewRate).stream()
                .map(hotel -> HotelDTO.toDto(hotel))
                .collect(Collectors.toList());
    }
	
}
