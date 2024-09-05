package com.ict.traveljoy.place.hotel.service;

import com.ict.traveljoy.place.hotel.repository.Hotel;
import com.ict.traveljoy.place.hotel.repository.HotelRepository;
import com.ict.traveljoy.place.region.repository.Region;
import com.ict.traveljoy.place.region.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RegionRepository regionRepository; 

    // 모든 숙소 검색
    public List<HotelDTO> findAllHotels() {
        return hotelRepository.findAll().stream()
                .map(HotelDTO::toDto)
                .collect(Collectors.toList());
    }

    // ID로 숙소 검색
    public Optional<HotelDTO> findHotelById(Long id) {
        return hotelRepository.findById(id)
                .map(HotelDTO::toDto);
    }

    // 숙소 저장
    @Transactional
    public HotelDTO saveHotel(HotelDTO hotelDTO) {
        if (hotelDTO.getHotelName() == null || hotelDTO.getHotelName().isEmpty()) {
            throw new IllegalArgumentException("숙소 이름이 비어있어요");
        }

        // 동일한 이름을 가진 첫 번째 Region 사용
        List<Region> regions = regionRepository.findByName(hotelDTO.getRegionName());
        if (regions.isEmpty()) {
            throw new IllegalArgumentException("주어진 지역이 존재하지 않아요");
        }
        Region region = regions.get(0);  // 첫 번째 Region을 사용

        Hotel hotel = hotelDTO.toEntity(region);
        hotel = hotelRepository.save(hotel);
        return HotelDTO.toDto(hotel);
    }

    // 숙소 수정
    @Transactional
    public HotelDTO updateHotel(Long id, HotelDTO hotelDTO) {
        Optional<Hotel> hotelOpt = hotelRepository.findById(id);

        if (hotelOpt.isPresent()) {
            Hotel hotel = hotelOpt.get();

            if (hotelDTO.getRegionName() != null) {
                // 동일한 이름을 가진 첫 번째 Region 사용
                List<Region> regions = regionRepository.findByName(hotelDTO.getRegionName());
                if (regions.isEmpty()) {
                    throw new IllegalArgumentException("주어진 지역이 존재하지 않아요");
                }
                Region region = regions.get(0);  // 첫 번째 Region을 사용
                hotel.setRegion(region);
            }

            hotel.setHotelName(hotelDTO.getHotelName());
            hotel.setAveragePrice(hotelDTO.getAveragePrice());
            hotel.setImageUrls(hotelDTO.getImageUrls());
            hotel.setAverageReviewRate(hotelDTO.getAverageReviewRate());
            hotel.setCheckInDate(hotelDTO.getCheckInDate());
            hotel.setCheckOutDate(hotelDTO.getCheckOutDate());

            Hotel updatedHotel = hotelRepository.save(hotel);
            return HotelDTO.toDto(updatedHotel);
        } else {
            throw new IllegalArgumentException("주어진 번호의 숙소를 찾을 수 없어요");
        }
    }

    // 숙소 삭제
    @Transactional
    public void deleteHotel(Long id) {
        if (hotelRepository.existsById(id)) {
            hotelRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("주어진 번호의 숙소를 찾을 수 없어요");
        }
    }

    // 숙소 이름으로 검색
    public List<HotelDTO> findHotelsByName(String hotelName) {
        return hotelRepository.findByHotelName(hotelName).stream()
                .map(HotelDTO::toDto)
                .collect(Collectors.toList());
    }

    // 지역 이름과 날짜를 기반으로 숙소 검색
    public List<HotelDTO> findHotelsByRegionNameAndDates(String regionName, LocalDate checkInDate, LocalDate checkOutDate) {
        return hotelRepository.findByRegionNameContainingAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(
                regionName, checkInDate, checkOutDate).stream()
                .map(HotelDTO::toDto)
                .collect(Collectors.toList());
    }
}
