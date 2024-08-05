package com.ict.traveljoy.place.placeInterest.service;


import com.ict.traveljoy.place.placeInterest.repository.PlaceInterest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceInterestDTO {
    private Long id;
    private Long interestId;
    private boolean isEvent;
    private boolean isFood;
    private boolean isSight;
    private boolean isHotel;
    
    public PlaceInterest toEntity() {
        return PlaceInterest.builder()
                            .id(id)
                            .interestId(interestId)
                            .isEvent(isEvent)
                            .isFood(isFood)
                            .isSight(isSight)
                            .isHotel(isHotel)
                            .build();
    }
    
    public static PlaceInterestDTO toDto(PlaceInterest placeInterest) {
        return PlaceInterestDTO.builder()
                               .id(placeInterest.getId())
                               .interestId(placeInterest.getInterestId())
                               .isEvent(placeInterest.getIsEvent())
                               .isFood(placeInterest.getIsFood())
                               .isSight(placeInterest.getIsSight())
                               .isHotel(placeInterest.getIsHotel())
                               .build();
    }
}
