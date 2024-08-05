package com.ict.traveljoy.plan.progress.service;

import com.ict.traveljoy.plan.repository.Plan;
import com.ict.traveljoy.plan.progress.repository.PlanProgress3;
import com.ict.traveljoy.plan.progress.repository.PlanProgress3Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlanProgress3Service {

    private final PlanProgress3Repository planProgress3Repository;

    @Autowired
    public PlanProgress3Service(PlanProgress3Repository planProgress3Repository) {
        this.planProgress3Repository = planProgress3Repository;
    }

    // 특정 계획 ID에 해당하는 PlanProgress3 엔티티를 조회하는 메서드
    public List<PlanProgress3Dto> getPlanProgressesByPlanId(Long planId) {
        List<PlanProgress3> planProgresses = planProgress3Repository.findByPlan_PlanId(planId);
        return planProgresses.stream()
                .map(PlanProgress3Dto::toDto)
                .collect(Collectors.toList());
    }

    // 특정 교통 여부에 해당하는 PlanProgress3 엔티티를 조회하는 메서드
    public List<PlanProgress3Dto> getPlanProgressesByIsTransportation(boolean isTransportation) {
        List<PlanProgress3> planProgresses = planProgress3Repository.findByIsTransportation(isTransportation);
        return planProgresses.stream()
                .map(PlanProgress3Dto::toDto)
                .collect(Collectors.toList());
    }

    // 특정 거리 여부에 해당하는 PlanProgress3 엔티티를 조회하는 메서드
    public List<PlanProgress3Dto> getPlanProgressesByIsDistance(boolean isDistance) {
        List<PlanProgress3> planProgresses = planProgress3Repository.findByIsDistance(isDistance);
        return planProgresses.stream()
                .map(PlanProgress3Dto::toDto)
                .collect(Collectors.toList());
    }

    // 특정 비용 여부에 해당하는 PlanProgress3 엔티티를 조회하는 메서드
    public List<PlanProgress3Dto> getPlanProgressesByIsPrice(boolean isPrice) {
        List<PlanProgress3> planProgresses = planProgress3Repository.findByIsPrice(isPrice);
        return planProgresses.stream()
                .map(PlanProgress3Dto::toDto)
                .collect(Collectors.toList());
    }

    // 특정 평가 여부에 해당하는 PlanProgress3 엔티티를 조회하는 메서드
    public List<PlanProgress3Dto> getPlanProgressesByIsRate(boolean isRate) {
        List<PlanProgress3> planProgresses = planProgress3Repository.findByIsRate(isRate);
        return planProgresses.stream()
                .map(PlanProgress3Dto::toDto)
                .collect(Collectors.toList());
    }

    // 특정 최소 비용에 해당하는 PlanProgress3 엔티티를 조회하는 메서드
    public List<PlanProgress3Dto> getPlanProgressesByMinimumCost(Long minimumCost) {
        List<PlanProgress3> planProgresses = planProgress3Repository.findByMinimumCost(minimumCost);
        return planProgresses.stream()
                .map(PlanProgress3Dto::toDto)
                .collect(Collectors.toList());
    }

    // 특정 최대 비용에 해당하는 PlanProgress3 엔티티를 조회하는 메서드
    public List<PlanProgress3Dto> getPlanProgressesByMaximumCost(Long maximumCost) {
        List<PlanProgress3> planProgresses = planProgress3Repository.findByMaximumCost(maximumCost);
        return planProgresses.stream()
                .map(PlanProgress3Dto::toDto)
                .collect(Collectors.toList());
    }

    // 특정 최소 평가에 해당하는 PlanProgress3 엔티티를 조회하는 메서드
    public List<PlanProgress3Dto> getPlanProgressesByMinimumRate(Integer minimumRate) {
        List<PlanProgress3> planProgresses = planProgress3Repository.findByMinimumRate(minimumRate);
        return planProgresses.stream()
                .map(PlanProgress3Dto::toDto)
                .collect(Collectors.toList());
    }

    // 특정 최대 평가에 해당하는 PlanProgress3 엔티티를 조회하는 메서드
    public List<PlanProgress3Dto> getPlanProgressesByMaximumRate(Integer maximumRate) {
        List<PlanProgress3> planProgresses = planProgress3Repository.findByMaximumRate(maximumRate);
        return planProgresses.stream()
                .map(PlanProgress3Dto::toDto)
                .collect(Collectors.toList());
    }
    
    // PlanProgress3 저장
    public PlanProgress3Dto savePlanProgress3(PlanProgress3Dto planProgress3Dto) {
        PlanProgress3 planProgress3 = planProgress3Dto.toEntity();
        PlanProgress3 savedPlanProgress3 = planProgress3Repository.save(planProgress3);
        return PlanProgress3Dto.toDto(savedPlanProgress3);
    }

    // PlanProgress3 수정
    public PlanProgress3Dto updatePlanProgress3(PlanProgress3Dto planProgress3Dto) {
        PlanProgress3 existingPlanProgress3 = planProgress3Repository.findById(planProgress3Dto.getPlanProgress3Id())
                .orElseThrow(() -> new IllegalArgumentException("ID가 " + planProgress3Dto.getPlanProgress3Id() + "인 PlanProgress3을(를) 찾을 수 없습니다."));

        if (planProgress3Dto.getPlanId() != null) {
            Plan plan = new Plan();
            plan.setPlanId(planProgress3Dto.getPlanId());
            existingPlanProgress3.setPlan(plan);
        }

        existingPlanProgress3.setIsTransportation(planProgress3Dto.getIsTransportation());
        existingPlanProgress3.setIsDistance(planProgress3Dto.getIsDistance());
        existingPlanProgress3.setIsPrice(planProgress3Dto.getIsPrice());
        existingPlanProgress3.setIsRate(planProgress3Dto.getIsRate());
        existingPlanProgress3.setMinimumCost(planProgress3Dto.getMinimumCost());
        existingPlanProgress3.setMaximumCost(planProgress3Dto.getMaximumCost());
        existingPlanProgress3.setMinimumRate(planProgress3Dto.getMinimumRate());
        existingPlanProgress3.setMaximumRate(planProgress3Dto.getMaximumRate());

        PlanProgress3 updatedPlanProgress3 = planProgress3Repository.save(existingPlanProgress3);
        return PlanProgress3Dto.toDto(updatedPlanProgress3);
    }

    // PlanProgress3 삭제
    public void deletePlanProgress3(Long planProgress3Id) {
        planProgress3Repository.deleteById(planProgress3Id);
    }
}
