package com.ict.traveljoy.plan.details.service;

import com.ict.traveljoy.plan.repository.Plan;
import com.ict.traveljoy.plan.details.repository.PlanInterest;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanInterestDto {

    private Long planInterestId;
    private Long planId; 
    private Long interestId;

    public PlanInterest toEntity() {
        Plan plan = new Plan();
        plan.setPlanId(planId);

        return PlanInterest.builder()
                .planInterestId(planInterestId)
                .plan(plan)
                .interestId(interestId)
                .build();
    }

    public static PlanInterestDto toDto(PlanInterest planInterest) {
        return PlanInterestDto.builder()
                .planInterestId(planInterest.getPlanInterestId())
                .planId(planInterest.getPlan().getPlanId())
                .interestId(planInterest.getInterestId())
                .build();
    }
}
