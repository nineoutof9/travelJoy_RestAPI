package com.ict.traveljoy.plan.details.service;

import com.ict.traveljoy.plan.details.repository.PlanHandicap;
import com.ict.traveljoy.plan.details.repository.PlanHandicapRepository;
import com.ict.traveljoy.plan.repository.Plan;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlanHandicapService {

    @Autowired
    private PlanHandicapRepository planHandicapRepository;

    
    public List<PlanHandicapDTO> getAllPlanHandicaps() {
        return planHandicapRepository.findAll().stream()
                .map(PlanHandicapDTO::toDto)
                .collect(Collectors.toList());
    }

    public PlanHandicapDTO getPlanHandicapById(Long id) {
        return planHandicapRepository.findById(id)
                .map(PlanHandicapDTO::toDto)
                .orElse(null); // 해당 ID가 없을 경우 null 처리
    }

    public List<PlanHandicapDTO> getPlanHandicapsByPlanId(Long plan_Id) {
        return planHandicapRepository.findByPlan_Id(plan_Id).stream()
                .map(PlanHandicapDTO::toDto)
                .collect(Collectors.toList());
    }

    public List<PlanHandicapDTO> getPlanHandicapsByHandicapId(Long handicap_Id) {
        return planHandicapRepository.findByHandicap_Id(handicap_Id).stream()
                .map(PlanHandicapDTO::toDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public PlanHandicapDTO savePlanHandicap(PlanHandicapDTO planHandicapDTO) {
        PlanHandicap planHandicap = planHandicapDTO.toEntity();
        PlanHandicap saveplanHandicap = planHandicapRepository.save(planHandicap);
        return PlanHandicapDTO.toDto(saveplanHandicap);
        
    }
    
    @Transactional
    public PlanHandicapDTO updatePlanHandicap(Long plan_Id, Long handicap_Id,PlanHandicapDTO planHandicapDTO) {
    	PlanHandicap existingPlanHandicap = planHandicapRepository.findByPlanIdAndHandicapId(plan_Id, handicap_Id);
    	if (existingPlanHandicap != null) {
    		Plan plan = new Plan();
    		plan.setId(planHandicapDTO.getPlan().getId());
    		
    		existingPlanHandicap.setPlan(plan);
    		
    		PlanHandicap updatePlanHandicap = planHandicapRepository.save(existingPlanHandicap);
    		return PlanHandicapDTO.toDto(updatePlanHandicap);


    	}
    	return null;
      
    	
    }
    
    

    @Transactional
    public void deletePlanHandicap(Long id) {
        planHandicapRepository.deleteById(id);
    }
}
