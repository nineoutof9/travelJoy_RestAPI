package com.ict.traveljoy.place.event.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    // 특정 지역의 이벤트 검색
    List<Event> findAllByRegionId(Long region_Id);

    // 이벤트 이름으로 검색
    List<Event> findByEventName(String eventName);

    // 특정 리뷰 평점 이상의 이벤트 검색
    List<Event> findByAverageReviewRateGreaterThanEqual(Float reviewRate);
    
    //lat, lng 기준 dist 거리 안에 있는것 중 날짜 범위 안에 있는 것들 가져오기
    @Query(value = "SELECT * FROM EVENT WHERE 6371 * ACOS(" +
            "COS(:lat * 3.141592653589793 / 180) * COS(LAT * 3.141592653589793 / 180) * " +
            "COS(LNG * 3.141592653589793 / 180 - :lng * 3.141592653589793 / 180) + " +
            "SIN(:lat * 3.141592653589793 / 180) * SIN(LAT * 3.141592653589793 / 180)) <= :dist " +
            "AND (event_start_date BETWEEN :startDate AND :endDate " +
            "OR event_end_date BETWEEN :startDate AND :endDate) " +
            "ORDER BY 6371 * ACOS(" +
            "COS(:lat * 3.141592653589793 / 180) * COS(LAT * 3.141592653589793 / 180) * " +
            "COS(LNG * 3.141592653589793 / 180 - :lng * 3.141592653589793 / 180) + " +
            "SIN(:lat * 3.141592653589793 / 180) * SIN(LAT * 3.141592653589793 / 180)) ASC",
    nativeQuery = true)
	List<Event> findEventsWithinDistanceAndDateRange(
	     @Param("lat") double latitude, 
	     @Param("lng") double longitude, 
	     @Param("dist") double distance,
	     @Param("startDate") LocalDate startDate, 
	     @Param("endDate") LocalDate endDate);

}
