package com.ict.traveljoy.service.planRegion;

import com.ict.traveljoy.repository.plan.Plan;
import com.ict.traveljoy.repository.region.Region;
import com.ict.traveljoy.repository.planRegion.PlanRegion;
import com.ict.traveljoy.repository.planRegion.PlanRegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlanRegionService {

    private final PlanRegionRepository planRegionRepository;

    @Autowired
    public PlanRegionService(PlanRegionRepository planRegionRepository) {
        this.planRegionRepository = planRegionRepository;
    }

    public List<PlanRegionDto> getPlanRegionsByPlanId(Long planId) {
        return planRegionRepository.findByPlan_PlanId(planId).stream()
                .map(PlanRegionDto::toDto)
                .collect(Collectors.toList());
    }

    public List<PlanRegionDto> getPlanRegionsByRegionId(Long regionId) {
        return planRegionRepository.findByRegion_Id(regionId).stream()
                .map(PlanRegionDto::toDto)
                .collect(Collectors.toList());
    }

    public PlanRegionDto getPlanRegionByPlanIdAndRegionId(Long planId, Long regionId) {
        return planRegionRepository.findByPlan_PlanIdAndRegion_Id(planId, regionId)
                .map(PlanRegionDto::toDto)
                .orElse(null);
    }

    public PlanRegionDto savePlanRegion(PlanRegionDto planRegionDto) {
        PlanRegion planRegion = planRegionDto.toEntity();
        PlanRegion savedPlanRegion = planRegionRepository.save(planRegion);
        return PlanRegionDto.toDto(savedPlanRegion);
    }

    public PlanRegionDto updatePlanRegion(PlanRegionDto planRegionDto) {
        return planRegionRepository.findById(planRegionDto.getPlanRegionId())
                .map(existingPlanRegion -> {

                    Plan plan = new Plan();
                    plan.setPlanId(planRegionDto.getPlanId());
                    existingPlanRegion.setPlan(plan);

                    Region region = new Region();
                    region.setId(planRegionDto.getRegionId());
                    existingPlanRegion.setRegion(region);

                    PlanRegion updatedPlanRegion = planRegionRepository.save(existingPlanRegion);
                    return PlanRegionDto.toDto(updatedPlanRegion);
                })
                .orElse(null); 
    }
    
    public void deletePlanRegionByPlanId(Long planId) {
        planRegionRepository.deleteByPlan_PlanId(planId);
    }

    public void deletePlanRegionByRegionId(Long regionId) {
        planRegionRepository.deleteByRegion_Id(regionId);
    }

}
