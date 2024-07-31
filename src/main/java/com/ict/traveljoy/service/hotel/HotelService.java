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

    @Autowired
    private HotelRepository hotelRepository;

    public List<HotelDTO> findAllHotels() {
        return hotelRepository.findAll().stream()
                .map(HotelDTO::toDto)
                .collect(Collectors.toList());
    }

    public Optional<HotelDTO> findHotelById(Long id) {
        return hotelRepository.findById(id)
                .map(HotelDTO::toDto);
    }

    public HotelDTO saveHotel(HotelDTO hotelDTO) {
        if (hotelDTO.getHotelname().isEmpty()) {
            throw new IllegalArgumentException("숙소 이름이 비어있어요");
        }
        Hotel hotel = hotelDTO.toEntity();
        hotel = hotelRepository.save(hotel);
        return HotelDTO.toDto(hotel);
    }

    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }

    public List<HotelDTO> findHotelsByRegionId(Long regionId) {
        return hotelRepository.findByRegion_Id(regionId).stream()
                .map(HotelDTO::toDto)
                .collect(Collectors.toList());
    }

    public List<HotelDTO> findHotelsByName(String hotelName) {
        return hotelRepository.findByHotelName(hotelName).stream()
                .map(HotelDTO::toDto)
                .collect(Collectors.toList());
    }

    public List<HotelDTO> findHotelsByPriceRange(float minPrice, float maxPrice) {
        return hotelRepository.findByAveragePriceBetween(minPrice, maxPrice).stream()
                .map(HotelDTO::toDto)
                .collect(Collectors.toList());
    }

    public List<HotelDTO> findHotelsByReviewCount(Long reviewCount) {
        return hotelRepository.findByTotalReviewCountGreaterThanEqual(reviewCount).stream()
                .map(HotelDTO::toDto)
                .collect(Collectors.toList());
    }

    public List<HotelDTO> findHotelsByReviewRate(float reviewRate) {
        return hotelRepository.findByAverageReviewRateGreaterThanEqual(reviewRate).stream()
                .map(HotelDTO::toDto)
                .collect(Collectors.toList());
    }
}