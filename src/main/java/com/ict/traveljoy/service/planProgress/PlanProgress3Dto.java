package com.ict.traveljoy.service.planProgress;

import com.ict.traveljoy.repository.plan.Plan;
import com.ict.traveljoy.repository.planProgress.PlanProgress3;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanProgress3Dto {

    private Long planProgress3Id;
    private Long planId;
    private boolean isTransportation;
    private boolean isDistance;
    private boolean isPrice;
    private boolean isRate;
    private Long minimumCost;
    private Long maximumCost;
    private Integer minimumRate;
    private Integer maximumRate;

    public PlanProgress3 toEntity() {
        Plan plan = new Plan();
        plan.setPlanId(planId);

        return PlanProgress3.builder()
                .planProgress3Id(planProgress3Id)
                .plan(plan)
                .isTransportation(isTransportation)
                .isDistance(isDistance)
                .isPrice(isPrice)
                .isRate(isRate)
                .minimumCost(minimumCost)
                .maximumCost(maximumCost)
                .minimumRate(minimumRate)
                .maximumRate(maximumRate)
                .build();
    }

    public static PlanProgress3Dto toDto(PlanProgress3 planProgress3) {
        return PlanProgress3Dto.builder()
                .planProgress3Id(planProgress3.getPlanProgress3Id())
                .planId(planProgress3.getPlan() != null ? planProgress3.getPlan().getPlanId() : null)
                .isTransportation(planProgress3.getIsTransportation())
                .isDistance(planProgress3.getIsDistance())
                .isPrice(planProgress3.getIsPrice())
                .isRate(planProgress3.getIsRate())
                .minimumCost(planProgress3.getMinimumCost())
                .maximumCost(planProgress3.getMaximumCost())
                .minimumRate(planProgress3.getMinimumRate())
                .maximumRate(planProgress3.getMaximumRate())
                .build();
    }
}
