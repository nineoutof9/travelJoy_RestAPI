package com.ict.traveljoy.plan.details.service;

import com.ict.traveljoy.plan.details.repository.PlanHandicap;
import com.ict.traveljoy.plan.repository.Plan;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanHandicapDto {

    private Long planHandicapId;
    private Long planId;
    private Long handicapId;

    public PlanHandicap toEntity() {
        Plan plan = new Plan();
        plan.setPlanId(planId);

        return PlanHandicap.builder()
                .planHandicapId(planHandicapId)
                .plan(plan)
                .handicapId(handicapId)
                .build();
    }

    public static PlanHandicapDto toDto(PlanHandicap planHandicap) {
        return PlanHandicapDto.builder()
                .planHandicapId(planHandicap.getPlanHandicapId())
                .planId(planHandicap.getPlan().getPlanId()) 
                .handicapId(planHandicap.getHandicapId())
                .build();
    }
}
