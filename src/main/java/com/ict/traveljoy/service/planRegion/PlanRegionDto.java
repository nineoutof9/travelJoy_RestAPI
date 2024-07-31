package com.ict.traveljoy.service.planRegion;

import com.ict.traveljoy.repository.plan.Plan;
import com.ict.traveljoy.repository.planRegion.PlanRegion;
import com.ict.traveljoy.repository.region.Region;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanRegionDto {
    private Long planRegionId;
    private Long planId;
    private Long regionId;

    public PlanRegion toEntity() {
        Plan plan = new Plan();
        plan.setPlanId(planId);

        Region region = new Region();
        region.setId(regionId);

        return PlanRegion.builder()
                .planRegionId(planRegionId)
                .plan(plan)
                .region(region)
                .build();
    }

    public static PlanRegionDto toDto(PlanRegion planRegion) {
        return PlanRegionDto.builder()
                .planRegionId(planRegion.getPlanRegionId())
                .planId(planRegion.getPlan().getPlanId())
                .regionId(planRegion.getRegion().getId())
                .build();
    }
}
