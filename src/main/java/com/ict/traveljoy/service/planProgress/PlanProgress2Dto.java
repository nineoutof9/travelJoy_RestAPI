package com.ict.traveljoy.service.planProgress;

import com.ict.traveljoy.repository.plan.Plan;
import com.ict.traveljoy.repository.planProgress.PlanProgress2;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanProgress2Dto {

    private Long planProgress2Id;
    private Long planId;
    private String detailPlanName;
    private Timestamp detailPlanStartDate;
    private Timestamp detailPlanEndDate;
    private char isEvent;
    private char isFood;
    private char isSight;
    private char isHotel;
    private Long eventId;
    private Long foodId;
    private Long sightId;
    private Long hotelId;

    public PlanProgress2 toEntity() {
        Plan plan = new Plan();
        plan.setPlanId(planId);

        return PlanProgress2.builder()
                .planProgress2Id(planProgress2Id)
                .plan(plan)
                .detailPlanName(detailPlanName)
                .detailPlanStartDate(detailPlanStartDate)
                .detailPlanEndDate(detailPlanEndDate)
                .isEvent(isEvent)
                .isFood(isFood)
                .isSight(isSight)
                .isHotel(isHotel)
                .eventId(eventId)
                .foodId(foodId)
                .sightId(sightId)
                .hotelId(hotelId)
                .build();
    }

    public static PlanProgress2Dto toDto(PlanProgress2 planProgress2) {
        return PlanProgress2Dto.builder()
                .planProgress2Id(planProgress2.getPlanProgress2Id())
                .planId(planProgress2.getPlan() != null ? planProgress2.getPlan().getPlanId() : null)
                .detailPlanName(planProgress2.getDetailPlanName())
                .detailPlanStartDate(planProgress2.getDetailPlanStartDate())
                .detailPlanEndDate(planProgress2.getDetailPlanEndDate())
                .isEvent(planProgress2.getIsEvent())
                .isFood(planProgress2.getIsFood())
                .isSight(planProgress2.getIsSight())
                .isHotel(planProgress2.getIsHotel())
                .eventId(planProgress2.getEventId())
                .foodId(planProgress2.getFoodId())
                .sightId(planProgress2.getSightId())
                .hotelId(planProgress2.getHotelId())
                .build();
    }
}
