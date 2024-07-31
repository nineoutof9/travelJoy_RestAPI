package com.ict.traveljoy.repository.planProgress;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanProgress3Repository extends JpaRepository<PlanProgress3, Long> {

    // 특정 계획 ID에 해당하는 PlanProgress3 엔티티를 조회하는 메서드
    List<PlanProgress3> findByPlan_PlanId(Long planId);

    // 특정 교통 여부에 해당하는 PlanProgress3 엔티티를 조회하는 메서드
    List<PlanProgress3> findByIsTransportation(boolean isTransportation);

    // 특정 거리 여부에 해당하는 PlanProgress3 엔티티를 조회하는 메서드
    List<PlanProgress3> findByIsDistance(boolean isDistance);

    // 특정 비용 여부에 해당하는 PlanProgress3 엔티티를 조회하는 메서드
    List<PlanProgress3> findByIsPrice(boolean isPrice);

    // 특정 평가 여부에 해당하는 PlanProgress3 엔티티를 조회하는 메서드
    List<PlanProgress3> findByIsRate(boolean isRate);

    // 특정 최소 비용에 해당하는 PlanProgress3 엔티티를 조회하는 메서드
    List<PlanProgress3> findByMinimumCost(Long minimumCost);

    // 특정 최대 비용에 해당하는 PlanProgress3 엔티티를 조회하는 메서드
    List<PlanProgress3> findByMaximumCost(Long maximumCost);

    // 특정 최소 평가에 해당하는 PlanProgress3 엔티티를 조회하는 메서드
    List<PlanProgress3> findByMinimumRate(Integer minimumRate);

    // 특정 최대 평가에 해당하는 PlanProgress3 엔티티를 조회하는 메서드
    List<PlanProgress3> findByMaximumRate(Integer maximumRate);

}
