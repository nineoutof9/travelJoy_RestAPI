package com.ict.traveljoy.place.hotel.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    List<Hotel> findByHotelName(String hotelName);

    // 주소와 날짜를 기준으로 호텔 검색
    List<Hotel> findByAddressContainingAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(
            String address, LocalDate checkInDate, LocalDate checkOutDate);
}
