package com.ict.traveljoy.plan.details.service;

import com.ict.traveljoy.plan.repository.Plan;
import com.ict.traveljoy.plan.details.repository.PlanInterest;
import com.ict.traveljoy.plan.details.repository.PlanInterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlanInterestService {

    private final PlanInterestRepository planInterestRepository;

    @Autowired
    public PlanInterestService(PlanInterestRepository planInterestRepository) {
        this.planInterestRepository = planInterestRepository;
    }

    // planId로 PlanInterest 조회
    public List<PlanInterestDTO> getPlanInterestsByPlanId(Long plan_Id) {
        List<PlanInterest> planInterests = planInterestRepository.findByPlanId(plan_Id);
        return planInterests.stream()
                .map(PlanInterestDTO::toDto)
                .collect(Collectors.toList());
    }

    // interestId로 PlanInterest 조회
    public List<PlanInterestDTO> getPlanInterestsByInterestId(Long interest_Id) {
        List<PlanInterest> planInterests = planInterestRepository.findByInterestId(interest_Id);
        return planInterests.stream()
                .map(PlanInterestDTO::toDto)
                .collect(Collectors.toList());
    }

    // planId와 interestId로 PlanInterest 조회
    public PlanInterestDTO getPlanInterestByPlanIdAndInterestId(Long plan_Id, Long interest_Id) {
        PlanInterest planInterest = planInterestRepository.findByPlanIdAndInterestId(plan_Id, interest_Id);
        if (planInterest != null) {
            return PlanInterestDTO.toDto(planInterest);
        }
        return null;
    }

    // 특정 관심사에 해당하는 PlanInterest 조회
    public List<PlanInterestDTO> getPlanInterestsByInterestIds(List<Long> interest_Ids) {
        List<PlanInterest> planInterests = planInterestRepository.findByInterestIdIn(interest_Ids);
        return planInterests.stream()
                .map(PlanInterestDTO::toDto)
                .collect(Collectors.toList());
    }

    // PlanInterest 저장
    public PlanInterestDTO savePlanInterest(PlanInterestDTO planInterestDTO) {
        PlanInterest planInterest = planInterestDTO.toEntity();
        PlanInterest savedPlanInterest = planInterestRepository.save(planInterest);
        return PlanInterestDTO.toDto(savedPlanInterest);
    }

    // PlanInterest 수정
    public PlanInterestDTO updatePlanInterest(PlanInterestDTO planInterestDTO) {
        PlanInterest existingPlanInterest = planInterestRepository.findByPlanIdAndInterestId(planInterestDTO.getPlan().getId(), planInterestDTO.getInterest().getId());
        if (existingPlanInterest != null) {
            Plan plan = new Plan();
            plan.setId(planInterestDTO.getPlan().getId());
            
            existingPlanInterest.setPlan(plan);
            // 필요한 경우 다른 필드도 업데이트할 수 있습니다.
            
            PlanInterest updatedPlanInterest = planInterestRepository.save(existingPlanInterest);
            return PlanInterestDTO.toDto(updatedPlanInterest);
        }
        return null; // 수정할 PlanInterest가 없는 경우
    }

    // planId로 PlanInterest 삭제
    public void deletePlanInterestByPlanId(Long plan_Id) {
        planInterestRepository.deleteByPlanId(plan_Id);
    }
}
