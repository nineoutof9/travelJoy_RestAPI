package com.ict.traveljoy.plan.progress.service;

import com.ict.traveljoy.plan.repository.Plan;
import com.ict.traveljoy.plan.repository.PlanRepository;
import com.ict.traveljoy.plan.progress.repository.PlanProgress2;
import com.ict.traveljoy.plan.progress.repository.PlanProgress2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlanProgress2Service {

    private final PlanProgress2Repository planProgress2Repository;
    private final PlanRepository planRepository; // PlanRepository 추가 필요

    @Autowired
    public PlanProgress2Service(PlanProgress2Repository planProgress2Repository, PlanRepository planRepository) {
        this.planProgress2Repository = planProgress2Repository;
        this.planRepository = planRepository;
    }

    // 특정 계획 ID에 해당하는 PlanProgress2 엔티티를 조회하는 메서드
    public List<PlanProgress2DTO> getPlanProgressesByPlanId(Long planId) {
        List<PlanProgress2> planProgresses = planProgress2Repository.findByPlanId(planId);
        return planProgresses.stream()
                .map(PlanProgress2DTO::toDto)
                .collect(Collectors.toList());
    }

    // 특정 이벤트 여부에 해당하는 PlanProgress2 엔티티를 조회하는 메서드
    public List<PlanProgress2DTO> getPlanProgressesByIsEvent(Integer isEvent) {
        List<PlanProgress2> planProgresses = planProgress2Repository.findByIsEvent(isEvent);
        return planProgresses.stream()
                .map(PlanProgress2DTO::toDto)
                .collect(Collectors.toList());
    }

    // 특정 식사 여부에 해당하는 PlanProgress2 엔티티를 조회하는 메서드
    public List<PlanProgress2DTO> getPlanProgressesByIsFood(Integer isFood) {
        List<PlanProgress2> planProgresses = planProgress2Repository.findByIsFood(isFood);
        return planProgresses.stream()
                .map(PlanProgress2DTO::toDto)
                .collect(Collectors.toList());
    }

    // 특정 관광지 여부에 해당하는 PlanProgress2 엔티티를 조회하는 메서드
    public List<PlanProgress2DTO> getPlanProgressesByIsSight(Integer isSight) {
        List<PlanProgress2> planProgresses = planProgress2Repository.findByIsSight(isSight);
        return planProgresses.stream()
                .map(PlanProgress2DTO::toDto)
                .collect(Collectors.toList());
    }

    // 특정 숙박 여부에 해당하는 PlanProgress2 엔티티를 조회하는 메서드
    public List<PlanProgress2DTO> getPlanProgressesByIsHotel(Integer isHotel) {
        List<PlanProgress2> planProgresses = planProgress2Repository.findByIsHotel(isHotel);
        return planProgresses.stream()
                .map(PlanProgress2DTO::toDto)
                .collect(Collectors.toList());
    }

    // 특정 계획 ID와 상세 계획 시작일, 종료일에 해당하는 PlanProgress2 엔티티를 조회하는 메서드
    public List<PlanProgress2DTO> getPlanProgressesByPlanIdAndDetailPlanStartDateBetween(Long plan_Id, Timestamp start_Date, Timestamp end_Date) {
        List<PlanProgress2> planProgresses = planProgress2Repository.findByPlanIdAndDetailPlanStartDateBetween(plan_Id, start_Date, end_Date);
        return planProgresses.stream()
                .map(PlanProgress2DTO::toDto)
                .collect(Collectors.toList());
    }

    // PlanProgress2 저장
    public PlanProgress2DTO savePlanProgress2(PlanProgress2DTO planProgress2DTO) {
        PlanProgress2 planProgress2 = planProgress2DTO.toEntity();

        // Plan 엔티티 설정
        if (planProgress2DTO.getPlan() != null) {
            Plan plan = planRepository.findById(planProgress2DTO.getPlan().getId()).orElse(null);
            planProgress2.setPlan(plan);
        }

        PlanProgress2 savedPlanProgress2 = planProgress2Repository.save(planProgress2);
        return PlanProgress2DTO.toDto(savedPlanProgress2);
    }

    // PlanProgress2 수정
    public PlanProgress2DTO updatePlanProgress2(PlanProgress2DTO planProgress2DTO) {
        Optional<PlanProgress2> existingPlanProgress2Opt = planProgress2Repository.findById(planProgress2DTO.getId());

        if (existingPlanProgress2Opt.isPresent()) {
            PlanProgress2 existingPlanProgress2 = existingPlanProgress2Opt.get();
            
            existingPlanProgress2.setDetailPlanName(planProgress2DTO.getDetailPlanName());
            existingPlanProgress2.setDetailPlanStartDate(planProgress2DTO.getDetailPlanStartDate());
            existingPlanProgress2.setDetailPlanEndDate(planProgress2DTO.getDetailPlanEndDate());
            existingPlanProgress2.setIsEvent(planProgress2DTO.getIsEvent() == true ? 1 : 0);
            existingPlanProgress2.setIsFood(planProgress2DTO.getIsFood() == true ? 1 : 0);
            existingPlanProgress2.setIsSight(planProgress2DTO.getIsSight() == true ? 1 : 0);
            existingPlanProgress2.setIsHotel(planProgress2DTO.getIsHotel() == true ? 1 : 0);
            existingPlanProgress2.setEvent(planProgress2DTO.getEvent());
            existingPlanProgress2.setFood(planProgress2DTO.getFood());
            existingPlanProgress2.setSight(planProgress2DTO.getSight());
            existingPlanProgress2.setHotel(planProgress2DTO.getHotel());

            // Plan 엔티티를 조회하여 설정
            if (planProgress2DTO.getPlan() != null) {
                Plan plan = planRepository.findById(planProgress2DTO.getPlan().getId()).orElse(null);
                existingPlanProgress2.setPlan(plan);
            }

            PlanProgress2 updatedPlanProgress2 = planProgress2Repository.save(existingPlanProgress2);
            return PlanProgress2DTO.toDto(updatedPlanProgress2);
        }
        return null; // 수정할 PlanProgress2가 없는 경우
    }

    // PlanProgress2 삭제
    public void deletePlanProgress2(Long id) {
        planProgress2Repository.deleteById(id);
    }
}
