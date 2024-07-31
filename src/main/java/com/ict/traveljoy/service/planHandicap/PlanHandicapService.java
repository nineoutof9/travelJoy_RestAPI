package com.ict.traveljoy.service.planHandicap;

import com.ict.traveljoy.repository.planHandicap.PlanHandicap;
import com.ict.traveljoy.repository.planHandicap.PlanHandicapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanHandicapService {

    @Autowired
    private PlanHandicapRepository planHandicapRepository;

    public List<PlanHandicapDto> getAllPlanHandicaps() {
        return planHandicapRepository.findAll().stream()
                .map(PlanHandicapDto::toDto)
                .collect(Collectors.toList());
    }

    public PlanHandicapDto getPlanHandicapById(Long id) {
        return planHandicapRepository.findById(id)
                .map(PlanHandicapDto::toDto)
                .orElse(null); // 해당 ID가 없을 경우 null 처리
    }

    public List<PlanHandicapDto> getPlanHandicapsByPlanId(Long planId) {
        return planHandicapRepository.findByPlan_PlanId(planId).stream()
                .map(PlanHandicapDto::toDto)
                .collect(Collectors.toList());
    }

    public List<PlanHandicapDto> getPlanHandicapsByHandicapId(Long handicapId) {
        return planHandicapRepository.findByHandicapId(handicapId).stream()
                .map(PlanHandicapDto::toDto)
                .collect(Collectors.toList());
    }

    public PlanHandicapDto createOrUpdatePlanHandicap(PlanHandicapDto planHandicapDto) {
        PlanHandicap planHandicap = planHandicapDto.toEntity();
        planHandicap = planHandicapRepository.save(planHandicap);
        return PlanHandicapDto.toDto(planHandicap);
    }

    public void deletePlanHandicap(Long id) {
        planHandicapRepository.deleteById(id);
    }
}
