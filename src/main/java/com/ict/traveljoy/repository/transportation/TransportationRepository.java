package com.ict.traveljoy.repository.transportation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportationRepository extends JpaRepository<Transportation, Long> {

    // 이동 유형에 따른 검색
    List<Transportation> findByIsBus(char isBus);
    List<Transportation> findByIsTrain(char isTrain);
    List<Transportation> findByIsAirplane(char isAirplane);
    List<Transportation> findByIsDrive(char isDrive);

    // 특정 기간 내의 교통수단 검색
    List<Transportation> findByStartDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // 가격 범위 내의 교통수단 검색
    List<Transportation> findByPriceBetween(float minPrice, float maxPrice);
}
