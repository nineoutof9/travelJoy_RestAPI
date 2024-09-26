package com.ict.traveljoy.newplan;


import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.place.hotel.repository.Hotel;
import com.ict.traveljoy.place.region.repository.Region;
import com.ict.traveljoy.users.repository.Users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewPlanDTO {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String clusterData;
    private String counts;
    private String destination;
    private String options;
    private String plans;
    private String recommendHotel;
    private String selectedHotel;
    private String selectedPlaces;
    private String selectedPlan;
    private Users user;  // 사용자 정보 추가

    // DTO to Entity conversion
    public NewPlan toEntity() {
        return NewPlan.builder()
                .id(this.id)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .clusterData(this.clusterData)
                .counts(this.counts)
                .destination(this.destination)
                .options(this.options)
                .plans(this.plans)
                .recommendHotel(this.recommendHotel)
                .selectedHotel(this.selectedHotel)
                .selectedPlaces(this.selectedPlaces)
                .selectedPlan(this.selectedPlan)
                .user(this.user)  // 사용자 정보 설정
                .build();
    }

    // Entity to DTO conversion
    public static NewPlanDTO fromEntity(NewPlan newPlan) {
        return NewPlanDTO.builder()
                .id(newPlan.getId())
                .startDate(newPlan.getStartDate())
                .endDate(newPlan.getEndDate())
                .clusterData(newPlan.getClusterData())
                .counts(newPlan.getCounts())
                .destination(newPlan.getDestination())
                .options(newPlan.getOptions())
                .plans(newPlan.getPlans())
                .recommendHotel(newPlan.getRecommendHotel())
                .selectedHotel(newPlan.getSelectedHotel())
                .selectedPlaces(newPlan.getSelectedPlaces())
                .selectedPlan(newPlan.getSelectedPlan())
                .user(newPlan.getUser())  // 사용자 정보 반환
                .build();
    }
}
