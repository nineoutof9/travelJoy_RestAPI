package com.ict.traveljoy.plan.progress.service;

import com.ict.traveljoy.plan.repository.Plan;
import com.ict.traveljoy.plan.progress.repository.PlanProgress1;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanProgress1DTO {

    private Long id;
    private Date planStartDate;
    private Date planEndDate;
    private Integer travelers;
    private Long travelCost;
    private Plan plan;

    public PlanProgress1 toEntity() {
       
        return PlanProgress1.builder()
                .id(id)
                .planStartDate(planStartDate)
                .planEndDate(planEndDate)
                .travelers(travelers)
                .travelCost(travelCost)
                .plan(plan)
                .build();
    }

    public static PlanProgress1DTO toDto(PlanProgress1 planProgress1) {
        return PlanProgress1DTO.builder()
                .id(planProgress1.getId())
                .planStartDate(planProgress1.getPlanStartDate())
                .planEndDate(planProgress1.getPlanEndDate())
                .travelers(planProgress1.getTravelers())
                .travelCost(planProgress1.getTravelCost())
                .plan(planProgress1.getPlan())
                .build();
    }
}
