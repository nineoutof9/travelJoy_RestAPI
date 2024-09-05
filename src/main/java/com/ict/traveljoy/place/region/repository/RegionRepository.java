package com.ict.traveljoy.place.region.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Long> {
    // 여러 개의 결과를 반환할 수 있도록 Optional 대신 List를 사용
    List<Region> findByName(String name);
}
