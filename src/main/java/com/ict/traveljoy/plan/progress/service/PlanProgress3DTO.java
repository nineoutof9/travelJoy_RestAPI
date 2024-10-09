package com.ict.traveljoy.plan.progress.service;

import com.ict.traveljoy.plan.repository.Plan;
import com.ict.traveljoy.plan.progress.repository.PlanProgress3;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanProgress3DTO {

    private Long id;
    private Plan plan;
    private Boolean isTransportation;
    private Boolean isDistance;
    private Boolean isPrice;
    private Boolean isRate;
    private Long minimumCost;
    private Long maximumCost;
    private Integer minimumRate;
    private Integer maximumRate;

    public PlanProgress3 toEntity() {

        return PlanProgress3.builder()
                .id(id)
                .plan(plan)
                .isTransportation(isTransportation == true ? 1 : 0)
                .isDistance(isDistance == true ? 1 : 0)
                .isPrice(isPrice == true ? 1 : 0)
                .isRate(isRate == true ? 1 : 0)
                .minimumCost(minimumCost)
                .maximumCost(maximumCost)
                .minimumRate(minimumRate)
                .maximumRate(maximumRate)
                .build();
    }

    public static PlanProgress3DTO toDto(PlanProgress3 planProgress3) {
        return PlanProgress3DTO.builder()
                .id(planProgress3.getId())
                .plan(planProgress3.getPlan())
                .isTransportation(planProgress3.getIsTransportation() == 1 ? true : false)
                .isDistance(planProgress3.getIsDistance() == 1 ? true : false)
                .isPrice(planProgress3.getIsPrice() == 1 ? true : false)
                .isRate(planProgress3.getIsRate() == 1 ? true : false)
                .minimumCost(planProgress3.getMinimumCost())
                .maximumCost(planProgress3.getMaximumCost())
                .minimumRate(planProgress3.getMinimumRate())
                .maximumRate(planProgress3.getMaximumRate())
                .build();
    }
}
