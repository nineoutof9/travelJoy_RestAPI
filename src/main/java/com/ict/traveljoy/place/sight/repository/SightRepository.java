package com.ict.traveljoy.place.sight.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ict.traveljoy.place.region.repository.Region;

public interface SightRepository extends JpaRepository<Sight, Long> {
    // Region 객체 대신 지역 이름으로 검색
    List<Sight> findByRegion_Name(String regionName);
    List<Sight> findBySightName(String sightName);
    List<Sight> findByAverageReviewRateGreaterThanEqual(float reviewRate);
}
