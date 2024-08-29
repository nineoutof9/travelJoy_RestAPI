package com.ict.traveljoy.plan.service;

import com.ict.traveljoy.plan.repository.Plan;
import com.ict.traveljoy.plan.repository.PlanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional

public class PlanService {

	@Autowired
    private PlanRepository planRepository;

	//모든 plan정보 검색
	public List<PlanDTO> findAllPlans(){
		return planRepository.findAll().stream()
				.map(plan -> PlanDTO.toDto(plan))
				.collect(Collectors.toList());
		
	}

    // planId로 Plans 조회
	public Optional<PlanDTO> getPlanById(Long id) {
	    return planRepository.findById(id).map(PlanDTO::toDto);
	}
	

    // isActive 여부로 Plan 목록 조회
    public List<PlanDTO> getPlansByIsActive(Integer isActive) {
        List<Plan> plans = planRepository.findByIsActive(isActive);
        return plans.stream()
                .map(PlanDTO::toDto)
                .collect(Collectors.toList());
    }

    // 특정 날짜 이후에 생성된 Plan 목록 조회
    public List<PlanDTO> getPlansCreatedAfter(LocalDateTime createDate) {
        List<Plan> plans = planRepository.findByCreateDateAfter(createDate);
        return plans.stream()
                .map(PlanDTO::toDto)
                .collect(Collectors.toList());
    }

    // 이름에 특정 단어가 포함된 Plan 목록 조회
    public List<PlanDTO> getPlansByKeyword(String keyword) {
        List<Plan> plans = planRepository.findByPlanNameContains(keyword);
        return plans.stream()
                .map(PlanDTO::toDto)
                .collect(Collectors.toList());
    }

    // Plan 저장
    public PlanDTO savePlan(PlanDTO planDto) {
        Plan plan = planDto.toEntity();
        Plan savedPlan = planRepository.save(plan);
        return PlanDTO.toDto(savedPlan);
    }

    // Plan 수정
    public PlanDTO updatePlan(PlanDTO planDto) {
        Plan existingPlan = planRepository.findById(planDto.getId()).get();
        if (existingPlan != null) {
            existingPlan.setPlanName(planDto.getPlanName());
            existingPlan.setPlanDescriptions(planDto.getPlanDescriptions());
            existingPlan.setCreateDate(planDto.getCreateDate());
            existingPlan.setIsActive(planDto.getIsActive() == true ? 1 : 0);
            existingPlan.setIsDelete(planDto.getIsDelete() == true ? 1 : 0);
            existingPlan.setDeleteDate(planDto.getDeleteDate());
            existingPlan.setProgress(planDto.getProgress());
            Plan updatedPlan = planRepository.save(existingPlan);
            return PlanDTO.toDto(updatedPlan);
        }
        return null; // 수정할 Plan이 없는 경우
    }

    // Plan 삭제 (논리 삭제)
    public boolean deletePlan(Long plan_Id) {
        Plan existingPlan = planRepository.findById(plan_Id).get();
        if (existingPlan != null) {
            existingPlan.setIsDelete(null);
            existingPlan.setDeleteDate(LocalDateTime.now());
            planRepository.save(existingPlan);
            return true;
        }
        return false; // 삭제할 Plan이 없는 경우
    }
}
