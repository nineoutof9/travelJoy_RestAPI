package com.ict.traveljoy.plan.details.service;

import com.ict.traveljoy.plan.repository.Plan;
import com.ict.traveljoy.info.interest.repository.Interest;
import com.ict.traveljoy.plan.details.repository.PlanInterest;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanInterestDTO {

    private Long id;
    private Plan plan; 
    private Interest interest;

    public PlanInterest toEntity() {
        
        return PlanInterest.builder()
                .id(id)
                .plan(plan)
                .interest(interest)
                .build();
    }

    public static PlanInterestDTO toDto(PlanInterest planInterest) {
        return PlanInterestDTO.builder()
                .id(planInterest.getId())
                .plan(planInterest.getPlan())
                .interest(planInterest.getInterest())
                .build();
    }
}
