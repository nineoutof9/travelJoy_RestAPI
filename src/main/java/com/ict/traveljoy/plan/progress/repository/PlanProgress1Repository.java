package com.ict.traveljoy.plan.progress.repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanProgress1Repository extends JpaRepository<PlanProgress1, Long> {
	
    // 특정 계획 기간 내의 PlanProgress1 엔티티를 조회하는 메서드
    List<PlanProgress1> findByPlanStartDateBetween(Date start_Date, Date end_Date);

    // 특정 여행자 수 이상인 PlanProgress1 엔티티를 조회하는 메서드
    List<PlanProgress1> findByTravelersGreaterThanEqual(Integer min_Travelers);

    // 특정 비용 이하의 PlanProgress1 엔티티를 조회하는 메서드
    List<PlanProgress1> findByTravelCostLessThanEqual(BigDecimal max_TravelCost);

    // 특정 계획 ID에 해당하는 PlanProgress1 엔티티를 조회하는 메서드
    List<PlanProgress1> findByPlanId(Long plan_Id);

    // 특정 계획 ID에 해당하는 PlanProgress1 엔티티를 삭제하는 메서드
    void deleteByPlanId(Long plan_Id);
}
