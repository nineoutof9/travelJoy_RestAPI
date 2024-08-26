package com.ict.traveljoy.plan.details.service;

import com.ict.traveljoy.plan.repository.Plan;
import com.ict.traveljoy.plan.details.repository.PlanRegion;
import com.ict.traveljoy.place.region.repository.Region;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanRegionDTO {
    private Long id;
    private Plan plan;
    private Region region;

    public PlanRegion toEntity() {

        return PlanRegion.builder()
                .id(id)
                .plan(plan)
                .region(region)
                .build();
    }

    public static PlanRegionDTO toDto(PlanRegion planRegion) {
        return PlanRegionDTO.builder()
                .id(planRegion.getId())
                .plan(planRegion.getPlan())
                .region(planRegion.getRegion())
                .build();
    }
}
