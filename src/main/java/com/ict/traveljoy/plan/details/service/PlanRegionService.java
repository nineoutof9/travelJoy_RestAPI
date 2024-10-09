package com.ict.traveljoy.plan.details.service;

import com.ict.traveljoy.plan.repository.Plan;
import com.ict.traveljoy.place.region.repository.Region;
import com.ict.traveljoy.plan.details.repository.PlanRegion;
import com.ict.traveljoy.plan.details.repository.PlanRegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlanRegionService {

    private final PlanRegionRepository planRegionRepository;

    @Autowired
    public PlanRegionService(PlanRegionRepository planRegionRepository) {
        this.planRegionRepository = planRegionRepository;
    }

    public List<PlanRegionDTO> getPlanRegionsByPlanId(Long plan_Id) {
        return planRegionRepository.findByPlanId(plan_Id).stream()
                .map(PlanRegionDTO::toDto)
                .collect(Collectors.toList());
    }

    public List<PlanRegionDTO> getPlanRegionsByRegionId(Long region_Id) {
        return planRegionRepository.findByRegionId(region_Id).stream()
                .map(PlanRegionDTO::toDto)
                .collect(Collectors.toList());
    }

    public PlanRegionDTO getPlanRegionByPlanIdAndRegionId(Long plan_Id, Long region_Id) {
        return planRegionRepository.findByPlanIdAndRegionId(plan_Id, region_Id)
                .map(PlanRegionDTO::toDto)
                .orElse(null);
    }

    public PlanRegionDTO savePlanRegion(PlanRegionDTO planRegionDTO) {
        PlanRegion planRegion = planRegionDTO.toEntity();
        PlanRegion savedPlanRegion = planRegionRepository.save(planRegion);
        return PlanRegionDTO.toDto(savedPlanRegion);
    }

    public PlanRegionDTO updatePlanRegion(PlanRegionDTO planRegionDTO) {
        return planRegionRepository.findById(planRegionDTO.getId())
                .map(existingPlanRegion -> {

                    Plan plan = new Plan();
                    plan.setId(planRegionDTO.getPlan().getId());
                    existingPlanRegion.setPlan(plan);

                    Region region = new Region();
                    region.setId(planRegionDTO.getRegion().getId());
                    existingPlanRegion.setRegion(region);

                    PlanRegion updatedPlanRegion = planRegionRepository.save(existingPlanRegion);
                    return PlanRegionDTO.toDto(updatedPlanRegion);
                })
                .orElse(null); 
    }
    
    public void deletePlanRegionByPlanId(Long plan_Id) {
        planRegionRepository.deleteByPlanId(plan_Id);
    }

    public void deletePlanRegionByRegionId(Long region_Id) {
        planRegionRepository.deleteByRegionId(region_Id);
    }

}
