package com.ict.traveljoy.move.service;

import com.ict.traveljoy.move.repository.Move;
import com.ict.traveljoy.plan.progress.repository.PlanProgress2;
import com.ict.traveljoy.place.transportation.repository.Transportation;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoveDTO {
    private Long id;
    private PlanProgress2 startDetailPlan;
    private PlanProgress2 endDetailPlan;
    private Transportation transportation;

    public Move toEntity() {
       return  Move.builder()
        .id(id)
        .startDetailPlan(startDetailPlan)
        .endDetailPlan(endDetailPlan)
        .transportation(transportation)
        .build();
    }

    public static MoveDTO toDto(Move move) {
        return MoveDTO.builder()
                .id(move.getId())
                .startDetailPlan(move.getStartDetailPlan())
                .endDetailPlan(move.getEndDetailPlan())
                .transportation(move.getTransportation())
                .build();
    }
}
