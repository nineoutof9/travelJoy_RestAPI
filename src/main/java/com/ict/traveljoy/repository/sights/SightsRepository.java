package com.ict.traveljoy.repository.sights;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SightsRepository extends JpaRepository<Sights, Long> {
    List<Sights> findByRegion_Id(Long regionId);
    List<Sights> findBySightName(String sightName);
    List<Sights> findByAverageReviewRate(float reviewRate);
}
