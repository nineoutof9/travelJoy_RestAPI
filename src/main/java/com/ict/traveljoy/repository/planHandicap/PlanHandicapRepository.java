package com.ict.traveljoy.repository.planHandicap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanHandicapRepository extends JpaRepository<PlanHandicap, Long> {

    List<PlanHandicap> findByPlan_PlanId(Long planId);

    List<PlanHandicap> findByHandicapId(Long handicapId);

    PlanHandicap findByPlan_PlanIdAndHandicapId(Long planId, Long handicapId);

    void deleteByPlan_PlanId(Long planId);

    void deleteByHandicapId(Long handicapId);
}
