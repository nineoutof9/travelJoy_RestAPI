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
    public List<PlanProgress3DTO> getPlanProgressesByPlanId(Long plan_Id) {
        List<PlanProgress3> planProgresses = planProgress3Repository.findByPlanId(plan_Id);
        return planProgresses.stream()
                .map(PlanProgress3DTO::toDto)
                .collect(Collectors.toList());
    }

    // 특정 교통 여부에 해당하는 PlanProgress3 엔티티를 조회하는 메서드
    public List<PlanProgress3DTO> getPlanProgressesByIsTransportation(Boolean isTransportation) {
        List<PlanProgress3> planProgresses = planProgress3Repository.findByIsTransportation(isTransportation== true ? 1 : 0);
        return planProgresses.stream()
                .map(PlanProgress3DTO::toDto)
                .collect(Collectors.toList());
    }

    // 특정 거리 여부에 해당하는 PlanProgress3 엔티티를 조회하는 메서드
    public List<PlanProgress3DTO> getPlanProgressesByIsDistance(Boolean isDistance) {
        List<PlanProgress3> planProgresses = planProgress3Repository.findByIsDistance(isDistance== true ? 1 : 0);
        return planProgresses.stream()
                .map(PlanProgress3DTO::toDto)
                .collect(Collectors.toList());
    }

    // 특정 비용 여부에 해당하는 PlanProgress3 엔티티를 조회하는 메서드
    public List<PlanProgress3DTO> getPlanProgressesByIsPrice(Boolean isPrice) {
        List<PlanProgress3> planProgresses = planProgress3Repository.findByIsPrice(isPrice== true ? 1 : 0);
        return planProgresses.stream()
                .map(PlanProgress3DTO::toDto)
                .collect(Collectors.toList());
    }

    // 특정 평가 여부에 해당하는 PlanProgress3 엔티티를 조회하는 메서드
    public List<PlanProgress3DTO> getPlanProgressesByIsRate(Boolean isRate) {
        List<PlanProgress3> planProgresses = planProgress3Repository.findByIsRate(isRate== true ? 1 : 0);
        return planProgresses.stream()
                .map(PlanProgress3DTO::toDto)
                .collect(Collectors.toList());
    }

    // 특정 최소 비용에 해당하는 PlanProgress3 엔티티를 조회하는 메서드
    public List<PlanProgress3DTO> getPlanProgressesByMinimumCost(Long minimum_Cost) {
        List<PlanProgress3> planProgresses = planProgress3Repository.findByMinimumCost(minimum_Cost);
        return planProgresses.stream()
                .map(PlanProgress3DTO::toDto)
                .collect(Collectors.toList());
    }

    // 특정 최대 비용에 해당하는 PlanProgress3 엔티티를 조회하는 메서드
    public List<PlanProgress3DTO> getPlanProgressesByMaximumCost(Long maximum_Cost) {
        List<PlanProgress3> planProgresses = planProgress3Repository.findByMaximumCost(maximum_Cost);
        return planProgresses.stream()
                .map(PlanProgress3DTO::toDto)
                .collect(Collectors.toList());
    }

    // 특정 최소 평가에 해당하는 PlanProgress3 엔티티를 조회하는 메서드
    public List<PlanProgress3DTO> getPlanProgressesByMinimumRate(Integer minimum_Rate) {
        List<PlanProgress3> planProgresses = planProgress3Repository.findByMinimumRate(minimum_Rate);
        return planProgresses.stream()
                .map(PlanProgress3DTO::toDto)
                .collect(Collectors.toList());
    }

    // 특정 최대 평가에 해당하는 PlanProgress3 엔티티를 조회하는 메서드
    public List<PlanProgress3DTO> getPlanProgressesByMaximumRate(Integer maximum_Rate) {
        List<PlanProgress3> planProgresses = planProgress3Repository.findByMaximumRate(maximum_Rate);
        return planProgresses.stream()
                .map(PlanProgress3DTO::toDto)
                .collect(Collectors.toList());
    }
    
    // PlanProgress3 저장
    public PlanProgress3DTO savePlanProgress3(PlanProgress3DTO planProgress3DTO) {
        PlanProgress3 planProgress3 = planProgress3DTO.toEntity();
        PlanProgress3 savedPlanProgress3 = planProgress3Repository.save(planProgress3);
        return PlanProgress3DTO.toDto(savedPlanProgress3);
    }

    // PlanProgress3 수정
    public PlanProgress3DTO updatePlanProgress3(PlanProgress3DTO planProgress3DTO) {
        PlanProgress3 existingPlanProgress3 = planProgress3Repository.findById(planProgress3DTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("ID가 " + planProgress3DTO.getId() + "인 PlanProgress3을(를) 찾을 수 없습니다."));

        if (planProgress3DTO.getPlan() != null) {
            Plan plan = new Plan();
            plan.setId(planProgress3DTO.getPlan().getId());
            existingPlanProgress3.setPlan(plan);
        }

        existingPlanProgress3.setIsTransportation(planProgress3DTO.getIsTransportation() == true ? 1 : 0);
        existingPlanProgress3.setIsDistance(planProgress3DTO.getIsDistance() == true ? 1 : 0);
        existingPlanProgress3.setIsPrice(planProgress3DTO.getIsPrice() == true ? 1 : 0);
        existingPlanProgress3.setIsRate(planProgress3DTO.getIsRate() == true ? 1 : 0);
        existingPlanProgress3.setMinimumCost(planProgress3DTO.getMinimumCost());
        existingPlanProgress3.setMaximumCost(planProgress3DTO.getMaximumCost());
        existingPlanProgress3.setMinimumRate(planProgress3DTO.getMinimumRate());
        existingPlanProgress3.setMaximumRate(planProgress3DTO.getMaximumRate());

        PlanProgress3 updatedPlanProgress3 = planProgress3Repository.save(existingPlanProgress3);
        return PlanProgress3DTO.toDto(updatedPlanProgress3);
    }

    // PlanProgress3 삭제
    public void deletePlanProgress3(Long planProgress3Id) {
        planProgress3Repository.deleteById(planProgress3Id);
    }
}
