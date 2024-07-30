package com.ict.traveljoy.repository.planRegion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanRegionRepository extends JpaRepository<PlanRegion, Long> {

    List<PlanRegion> findByPlan_PlanId(Long planId);

    List<PlanRegion> findByRegion_Id(Long regionId);

    Optional<PlanRegion> findByPlan_PlanIdAndRegion_Id(Long planId, Long regionId);

    void deleteByPlan_PlanId(Long planId);

    void deleteByRegion_Id(Long regionId);
}
