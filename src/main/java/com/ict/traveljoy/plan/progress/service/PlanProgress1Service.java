package com.ict.traveljoy.plan.progress.service;

import com.ict.traveljoy.plan.repository.Plan;
import com.ict.traveljoy.plan.repository.PlanRepository;
import com.ict.traveljoy.plan.progress.repository.PlanProgress1;
import com.ict.traveljoy.plan.progress.repository.PlanProgress1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlanProgress1Service {

    private final PlanProgress1Repository planProgress1Repository;
    private final PlanRepository planRepository; // PlanRepository 추가 필요

    @Autowired
    public PlanProgress1Service(PlanProgress1Repository planProgress1Repository, PlanRepository planRepository) {
        this.planProgress1Repository = planProgress1Repository;
        this.planRepository = planRepository;
    }

    // 특정 계획 기간 내의 PlanProgress1 엔티티를 조회하는 메서드
    public List<PlanProgress1DTO> getPlanProgressesByPlanStartDateBetween(Date start_Date, Date end_Date) {
        List<PlanProgress1> planProgresses = planProgress1Repository.findByPlanStartDateBetween(start_Date, end_Date);
        return planProgresses.stream()
                .map(PlanProgress1DTO::toDto)
                .collect(Collectors.toList());
    }

    // 특정 여행자 수 이상인 PlanProgress1 엔티티를 조회하는 메서드
    public List<PlanProgress1DTO> getPlanProgressesByTravelersGreaterThanEqual(Integer min_Travelers) {
        List<PlanProgress1> planProgresses = planProgress1Repository.findByTravelersGreaterThanEqual(min_Travelers);
        return planProgresses.stream()
                .map(PlanProgress1DTO::toDto)
                .collect(Collectors.toList());
    }

    // 특정 비용 이하의 PlanProgress1 엔티티를 조회하는 메서드
    public List<PlanProgress1DTO> getPlanProgressesByTravelCostLessThanEqual(BigDecimal max_TravelCost) {
        List<PlanProgress1> planProgresses = planProgress1Repository.findByTravelCostLessThanEqual(max_TravelCost);
        return planProgresses.stream()
                .map(PlanProgress1DTO::toDto)
                .collect(Collectors.toList());
    }

    // 특정 계획 ID에 해당하는 PlanProgress1 엔티티를 조회하는 메서드
    public List<PlanProgress1DTO> getPlanProgressesByPlanId(Long plan_Id) {
        List<PlanProgress1> planProgresses = planProgress1Repository.findByPlanId(plan_Id);
        return planProgresses.stream()
                .map(PlanProgress1DTO::toDto)
                .collect(Collectors.toList());
    }

    // PlanProgress1 저장
    public PlanProgress1DTO savePlanProgress1(PlanProgress1DTO planProgress1DTO) {
        PlanProgress1 planProgress1 = planProgress1DTO.toEntity();
        
        // Plan 엔티티 설정
        if (planProgress1DTO.getPlan() != null) {
            Plan plan = planRepository.findById(planProgress1DTO.getPlan().getId()).orElse(null);
            planProgress1.setPlan(plan);
        }

        PlanProgress1 savedPlanProgress1 = planProgress1Repository.save(planProgress1);
        return PlanProgress1DTO.toDto(savedPlanProgress1);
    }

    // PlanProgress1 수정
    public PlanProgress1DTO updatePlanProgress1(PlanProgress1DTO planProgress1DTO) {
        Optional<PlanProgress1> existingPlanProgress1Opt = planProgress1Repository.findById(planProgress1DTO.getId());

        if (existingPlanProgress1Opt.isPresent()) {
            PlanProgress1 existingPlanProgress1 = existingPlanProgress1Opt.get();

            existingPlanProgress1.setPlanStartDate(planProgress1DTO.getPlanStartDate());
            existingPlanProgress1.setPlanEndDate(planProgress1DTO.getPlanEndDate());
            existingPlanProgress1.setTravelers(planProgress1DTO.getTravelers());
            existingPlanProgress1.setTravelCost(planProgress1DTO.getTravelCost());

            // Plan 엔티티를 조회하여 설정
            if (planProgress1DTO.getPlan() != null) {
                Plan plan = planRepository.findById(planProgress1DTO.getPlan().getId()).orElse(null);
                existingPlanProgress1.setPlan(plan);
            }

            PlanProgress1 updatedPlanProgress1 = planProgress1Repository.save(existingPlanProgress1);
            return PlanProgress1DTO.toDto(updatedPlanProgress1);
        }
        return null; // 수정할 PlanProgress1이 없는 경우
    }

    // PlanProgress1 삭제
    public void deletePlanProgress1(Long id) {
        planProgress1Repository.deleteById(id);
    }
}
