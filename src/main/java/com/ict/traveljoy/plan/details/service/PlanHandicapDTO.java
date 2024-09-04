package com.ict.traveljoy.plan.details.service;

import com.ict.traveljoy.info.handicap.repository.Handicap;
import com.ict.traveljoy.plan.details.repository.PlanHandicap;
import com.ict.traveljoy.plan.repository.Plan;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanHandicapDTO {

    private Long id;
    private Plan plan;
    private Handicap handicap;

    public PlanHandicap toEntity() {
    	
        return PlanHandicap.builder()
                .id(id)
                .plan(plan)
                .handicap(handicap)
                .build();
    }

    public static PlanHandicapDTO toDto(PlanHandicap planHandicap) {
        return PlanHandicapDTO.builder()
                .id(planHandicap.getId())
                .plan(planHandicap.getPlan()) 
                .handicap(planHandicap.getHandicap())
                .build();
    }
}
