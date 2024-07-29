package com.ict.traveljoy.service.planProgress;

import com.ict.traveljoy.repository.plan.Plan;
import com.ict.traveljoy.repository.planProgress.PlanProgress1;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanProgress1Dto {

    private Long planProgress1Id;
    private Date planStartDate;
    private Date planEndDate;
    private Integer travelers;
    private BigDecimal travelCost;
    private Long planId;

    public PlanProgress1 toEntity() {
        Plan plan = new Plan();
        plan.setPlanId(planId);

        return PlanProgress1.builder()
                .planProgress1Id(planProgress1Id)
                .planStartDate(planStartDate)
                .planEndDate(planEndDate)
                .travelers(travelers)
                .travelCost(travelCost)
                .plan(plan)
                .build();
    }

    public static PlanProgress1Dto toDto(PlanProgress1 planProgress1) {
        return PlanProgress1Dto.builder()
                .planProgress1Id(planProgress1.getPlanProgress1Id())
                .planStartDate(planProgress1.getPlanStartDate())
                .planEndDate(planProgress1.getPlanEndDate())
                .travelers(planProgress1.getTravelers())
                .travelCost(planProgress1.getTravelCost())
                .planId(planProgress1.getPlan() != null ? planProgress1.getPlan().getPlanId() : null)
                .build();
    }
}
