package com.ict.traveljoy.repository.planProgress;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanProgress4Repository extends JpaRepository<PlanProgress4, Long> {

    // 특정 계획 ID에 해당하는 PlanProgress4 엔티티를 조회하는 메서드
    List<PlanProgress4> findByPlan_PlanId(Long planId);

    // 특정 AI 생성 계획 ID에 해당하는 PlanProgress4 엔티티를 조회하는 메서드
    List<PlanProgress4> findByAiMadePlan_PlanId(Long aiMadePlanId);

    // 특정 계획 ID와 AI 생성 계획 ID에 해당하는 PlanProgress4 엔티티를 조회하는 메서드
    List<PlanProgress4> findByPlan_PlanIdAndAiMadePlan_PlanId(Long planId, Long aiMadePlanId);

    // 특정 계획 ID에 해당하는 PlanProgress4 엔티티를 삭제하는 메서드
    void deleteByPlan_PlanId(Long planId);

    // 특정 AI 생성 계획 ID에 해당하는 PlanProgress4 엔티티를 삭제하는 메서드
    void deleteByAiMadePlan_PlanId(Long aiMadePlanId);
}
