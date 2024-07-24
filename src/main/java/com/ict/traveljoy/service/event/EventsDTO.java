package com.ict.traveljoy.service.event;

import java.time.LocalDateTime;

import com.ict.traveljoy.repository.event.Event;
import com.ict.traveljoy.repository.region.Region;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventsDTO {
    private Long id;
    private Region region;
    private char isHasImage;
    private LocalDateTime eventStartDate;
    private LocalDateTime eventEndDate;
    private Float entranceFee;
    private String eventName;
    private String descriptions;
    private String address;
    private Float lat;
    private Float lng;
    private Long totalReviewCount;
    private Float averageReviewRate;

    public Event toEntity() {
        return Event.builder()
                .id(id)
                .region(region)
                .isHasImage(isHasImage)
                .eventStartDate(eventStartDate)
                .eventEndDate(eventEndDate)
                .entranceFee(entranceFee)
                .eventName(eventName)
                .descriptions(descriptions)
                .address(address)
                .lat(lat)
                .lng(lng)
                .totalReviewCount(totalReviewCount)
                .averageReviewRate(averageReviewRate)
                .build();
    }

    public static EventsDTO toDto(Event events) {
        return EventsDTO.builder()
                .id(events.getId())
                .region(events.getRegion())
                .isHasImage(events.getIsHasImage())
                .eventStartDate(events.getEventStartDate())
                .eventEndDate(events.getEventEndDate())
                .entranceFee(events.getEntranceFee())
                .eventName(events.getEventName())
                .descriptions(events.getDescriptions())
                .address(events.getAddress())
                .lat(events.getLat())
                .lng(events.getLng())
                .totalReviewCount(events.getTotalReviewCount())
                .averageReviewRate(events.getAverageReviewRate())
                .build();
    }
}
