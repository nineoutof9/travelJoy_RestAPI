package com.ict.traveljoy.plan.details.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanRegionRepository extends JpaRepository<PlanRegion, Long> {

    List<PlanRegion> findByPlanId(Long plan_Id);

    List<PlanRegion> findByRegionId(Long region_Id);

    Optional<PlanRegion> findByPlanIdAndRegionId(Long plan_Id, Long region_Id);

    void deleteByPlanId(Long plan_Id);

    void deleteByRegionId(Long region_Id);
}
