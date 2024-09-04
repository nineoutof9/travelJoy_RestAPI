package com.ict.traveljoy.plan.progress.service;

import com.ict.traveljoy.plan.repository.Plan;
import com.ict.traveljoy.plan.repository.PlanRepository;
import com.ict.traveljoy.plan.progress.repository.PlanProgress4;
import com.ict.traveljoy.plan.progress.repository.PlanProgress4Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlanProgress4Service {

    private final PlanProgress4Repository planProgress4Repository;
    private final PlanRepository planRepository;

    @Autowired
    public PlanProgress4Service(PlanProgress4Repository planProgress4Repository, PlanRepository planRepository) {
        this.planProgress4Repository = planProgress4Repository;
        this.planRepository = planRepository;
    }

    // 특정 계획 ID에 해당하는 PlanProgress4 엔티티를 조회하는 메서드

    public List<PlanProgress4DTO> getPlanProgressesByPlanId(Long plan_Id) {

        List<PlanProgress4> planProgresses = planProgress4Repository.findByAiMadePlanId(plan_Id);
        return planProgresses.stream()
                .map(PlanProgress4DTO::toDto)
                .collect(Collectors.toList());
    }

    // 특정 AI 생성 계획 ID에 해당하는 PlanProgress4 엔티티를 조회하는 메서드

    public List<PlanProgress4DTO> getPlanProgressesByAiMadePlanId(Long aiMadePlan_Id) {

        List<PlanProgress4> planProgresses = planProgress4Repository.findByAiMadePlanId(aiMadePlan_Id);
        return planProgresses.stream()
                .map(PlanProgress4DTO::toDto)
                .collect(Collectors.toList());
    }

    // 특정 계획 ID와 AI 생성 계획 ID에 해당하는 PlanProgress4 엔티티를 조회하는 메서드

    public List<PlanProgress4DTO> getPlanProgressesByPlanIdAndAiMadePlanId(Long plan_Id, Long aiMadePlan_Id) {

        List<PlanProgress4> planProgresses = planProgress4Repository.findByPlanIdAndAiMadePlanId(plan_Id, aiMadePlan_Id);
        return planProgresses.stream()
                .map(PlanProgress4DTO::toDto)
                .collect(Collectors.toList());
    }

    // PlanProgress4 저장

    public PlanProgress4DTO savePlanProgress4(PlanProgress4DTO PlanProgress4DTO) {
        Plan plan = planRepository.findById(PlanProgress4DTO.getPlan().getId())
                .orElseThrow(() -> new IllegalArgumentException("ID가 " + PlanProgress4DTO.getPlan() + "인 Plan을 찾을 수 없습니다."));
        Plan aiMadePlan = planRepository.findById(PlanProgress4DTO.getAiMadePlan().getId())
                .orElseThrow(() -> new IllegalArgumentException("ID가 " + PlanProgress4DTO.getAiMadePlan() + "인 AI 생성 Plan을 찾을 수 없습니다."));


        PlanProgress4 planProgress4 = PlanProgress4.builder()
                .plan(plan)
                .aiMadePlan(aiMadePlan)
                .build();

        PlanProgress4 savedPlanProgress4 = planProgress4Repository.save(planProgress4);
        return PlanProgress4DTO.toDto(savedPlanProgress4);
    }

    // PlanProgress4 수정

    public PlanProgress4DTO updatePlanProgress4(PlanProgress4DTO PlanProgress4DTO) {
        PlanProgress4 existingPlanProgress4 = planProgress4Repository.findById(PlanProgress4DTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("ID가 " + PlanProgress4DTO.getId() + "인 PlanProgress4를 찾을 수 없습니다."));

        Plan plan = planRepository.findById(PlanProgress4DTO.getPlan().getId())
                .orElseThrow(() -> new IllegalArgumentException("ID가 " + PlanProgress4DTO.getPlan() + "인 Plan을 찾을 수 없습니다."));
        Plan aiMadePlan = planRepository.findById(PlanProgress4DTO.getAiMadePlan().getId())
                .orElseThrow(() -> new IllegalArgumentException("ID가 " + PlanProgress4DTO.getAiMadePlan() + "인 AI 생성 Plan을 찾을 수 없습니다."));


        existingPlanProgress4.setPlan(plan);
        existingPlanProgress4.setAiMadePlan(aiMadePlan);

        PlanProgress4 updatedPlanProgress4 = planProgress4Repository.save(existingPlanProgress4);
        return PlanProgress4DTO.toDto(updatedPlanProgress4);
    }

    // PlanProgress4 삭제
    public void deletePlanProgress4(Long planProgress4Id) {
        if (!planProgress4Repository.existsById(planProgress4Id)) {
            throw new IllegalArgumentException("ID가 " + planProgress4Id + "인 PlanProgress4를 찾을 수 없습니다.");
        }
        planProgress4Repository.deleteById(planProgress4Id);
    }
}
