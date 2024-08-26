package com.ict.traveljoy.plan.progress.service;

import com.ict.traveljoy.plan.repository.Plan;
import com.ict.traveljoy.plan.progress.repository.PlanProgress4;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanProgress4Dto {

    private Long id;
    private Plan plan;
    private Plan aiMadePlan;

    public PlanProgress4 toEntity() {

        return PlanProgress4.builder()
                .id(id)
                .plan(plan)
                .aiMadePlan(aiMadePlan)
                .build();
    }

    public static PlanProgress4Dto toDto(PlanProgress4 planProgress4) {
        return PlanProgress4Dto.builder()
                .id(planProgress4.getId())
                .plan(planProgress4.getPlan())
                .aiMadePlan(planProgress4.getAiMadePlan())
                .build();
    }
}
