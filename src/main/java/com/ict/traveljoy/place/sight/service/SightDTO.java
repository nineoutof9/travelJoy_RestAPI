package com.ict.traveljoy.place.sight.service;

import com.ict.traveljoy.place.region.repository.Region;
import com.ict.traveljoy.place.region.repository.RegionRepository;
import com.ict.traveljoy.place.sight.repository.Sight;
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
public class SightDTO {
    private Long id;
    private Long regionId; 
    private Boolean isHasImage;
    private Float entranceFee;
    private String sightName;
    private String descriptions;
    private String address;
    private Float lat;
    private Float lng;
    private Long totalReviewCount;
    private Float averageReviewRate;

    public Sight toEntity(RegionRepository regionRepository) {
        Region region = regionRepository.findById(regionId)
                                        .orElseThrow(() -> new IllegalArgumentException("Invalid RegionId"));

        return Sight.builder()
                .id(id)
                .region(region)
                .isHasImage(isHasImage != null && isHasImage ? 1 : 0)
                .entranceFee(entranceFee)
                .sightName(sightName)
                .descriptions(descriptions)
                .address(address)
                .lat(lat)
                .lng(lng)
                .totalReviewCount(totalReviewCount)
                .averageReviewRate(averageReviewRate)
                .build();
    }

    public static SightDTO toDto(Sight sight) {
        return SightDTO.builder()
                .id(sight.getId())
                .regionId(sight.getRegion() != null ? sight.getRegion().getId() : null) 
                .isHasImage(sight.getIsHasImage() == 1)
                .entranceFee(sight.getEntranceFee())
                .sightName(sight.getSightName())
                .descriptions(sight.getDescriptions())
                .address(sight.getAddress())
                .lat(sight.getLat())
                .lng(sight.getLng())
                .totalReviewCount(sight.getTotalReviewCount())
                .averageReviewRate(sight.getAverageReviewRate())
                .build();
    }
}
