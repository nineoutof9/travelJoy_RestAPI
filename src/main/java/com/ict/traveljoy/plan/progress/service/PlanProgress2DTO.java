package com.ict.traveljoy.plan.progress.service;

import com.ict.traveljoy.plan.repository.Plan;
import com.ict.traveljoy.place.event.repository.Event;
import com.ict.traveljoy.place.food.repository.Food;
import com.ict.traveljoy.place.hotel.repository.Hotel;
import com.ict.traveljoy.place.sight.repository.Sight;
import com.ict.traveljoy.plan.progress.repository.PlanProgress2;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanProgress2DTO {

    private Long id;
    private Plan plan;
    private String detailPlanName;
    private Timestamp detailPlanStartDate;
    private Timestamp detailPlanEndDate;
    private Boolean isEvent;
    private Boolean isFood;
    private Boolean isSight;
    private Boolean isHotel;
    private Event event;
    private Food food;
    private Sight sight;
    private Hotel hotel;

    public PlanProgress2 toEntity() {
      
        return PlanProgress2.builder()
                .id(id)
                .plan(plan)
                .detailPlanName(detailPlanName)
                .detailPlanStartDate(detailPlanStartDate)
                .detailPlanEndDate(detailPlanEndDate)
                .isEvent(isEvent == true ? 1 : 0)
                .isFood(isFood == true ? 1 : 0)
                .isSight(isSight == true ? 1 : 0)
                .isHotel(isHotel == true ? 1 : 0)
                .event(event)
                .food(food)
                .sight(sight)
                .hotel(hotel)
                .build();
    }

    public static PlanProgress2DTO toDto(PlanProgress2 planProgress2) {
        return PlanProgress2DTO.builder()
                .id(planProgress2.getId())
                .plan(planProgress2.getPlan())
                .detailPlanName(planProgress2.getDetailPlanName())
                .detailPlanStartDate(planProgress2.getDetailPlanStartDate())
                .detailPlanEndDate(planProgress2.getDetailPlanEndDate())
                .isEvent(planProgress2.getIsEvent()== 1 ? true : false)
                .isFood(planProgress2.getIsFood()== 1 ? true : false)
                .isSight(planProgress2.getIsSight()== 1 ? true : false)
                .isHotel(planProgress2.getIsHotel()== 1 ? true : false)
                .event(planProgress2.getEvent())
                .food(planProgress2.getFood())
                .sight(planProgress2.getSight())
                .hotel(planProgress2.getHotel())
                .build();
    }
}
