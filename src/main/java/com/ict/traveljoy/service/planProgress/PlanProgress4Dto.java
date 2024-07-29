package com.ict.traveljoy.service.planProgress;

import com.ict.traveljoy.repository.plan.Plan;
import com.ict.traveljoy.repository.planProgress.PlanProgress4;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanProgress4Dto {

    private Long planProgress4Id;
    private Long planId;
    private Long aiMadePlanId;

    public PlanProgress4 toEntity() {
        Plan plan = new Plan();
        plan.setPlanId(planId);

        Plan aiMadePlan = new Plan();
        aiMadePlan.setPlanId(aiMadePlanId);

        return PlanProgress4.builder()
                .planProgress4Id(planProgress4Id)
                .plan(plan)
                .aiMadePlan(aiMadePlan)
                .build();
    }

    public static PlanProgress4Dto toDto(PlanProgress4 planProgress4) {
        return PlanProgress4Dto.builder()
                .planProgress4Id(planProgress4.getPlanProgress4Id())
                .planId(planProgress4.getPlan() != null ? planProgress4.getPlan().getPlanId() : null)
                .aiMadePlanId(planProgress4.getAiMadePlan() != null ? planProgress4.getAiMadePlan().getPlanId() : null)
                .build();
    }
}
