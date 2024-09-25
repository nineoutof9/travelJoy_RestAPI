package com.ict.traveljoy.place.sight.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ict.traveljoy.place.region.repository.Region;

public interface SightRepository extends JpaRepository<Sight, Long> {
    // Region 객체 대신 지역 이름으로 검색
    List<Sight> findByRegion_Name(String regionName);
    List<Sight> findBySightName(String sightName);
    List<Sight> findByAverageReviewRateGreaterThanEqual(float reviewRate);
    
    @Query(value = "SELECT * FROM SIGHT WHERE 6371 * ACOS(" +
            "COS(:lat * 3.141592653589793 / 180) * COS(LAT * 3.141592653589793 / 180) * " +
            "COS(LNG * 3.141592653589793 / 180 - :lng * 3.141592653589793 / 180) + " +
            "SIN(:lat * 3.141592653589793 / 180) * SIN(LAT * 3.141592653589793 / 180)) <= :dist",
    nativeQuery = true)
	List<Sight> findSightsWithinDistance(
	     @Param("lat") double latitude, 
	     @Param("lng") double longitude, 
	     @Param("dist") double distance);
}
