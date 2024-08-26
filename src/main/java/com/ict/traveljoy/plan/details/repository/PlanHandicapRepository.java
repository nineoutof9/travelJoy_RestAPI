package com.ict.traveljoy.plan.details.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanHandicapRepository extends JpaRepository<PlanHandicap, Long> {

    List<PlanHandicap> findByPlan_Id(Long plan_Id);

    List<PlanHandicap> findByHandicap_Id(Long handicap_Id);

    PlanHandicap findByPlanIdAndHandicapId(Long plan_Id, Long handicap_Id);

    void deleteByPlanId(Long plan_Id);

    void deleteByHandicapId(Long handicap_Id);
}
