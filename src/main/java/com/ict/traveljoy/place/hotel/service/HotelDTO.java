package com.ict.traveljoy.place.hotel.service;

import com.ict.traveljoy.place.hotel.repository.Hotel;
import com.ict.traveljoy.place.region.repository.Region;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelDTO {
    private Long id;
    private Boolean isHasImage;
    private String averagePrice;
    private String hotelName;
    private String regionName; 
    private Float averageReviewRate;
    private List<String> imageUrls;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Float lat;
    private Float lng;
    

    public Hotel toEntity(Region region) {
        return Hotel.builder()
                .id(id)
                .isHasImage(isHasImage != null && isHasImage ? 1 : 0)
                .averagePrice(averagePrice)
                .hotelName(hotelName)
                .region(region)
                .imageUrls(imageUrls)
                .averageReviewRate(averageReviewRate)
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .lat(lat)
                .lng(lng)
                .build();
    }

    public static HotelDTO toDto(Hotel hotel) {
        return HotelDTO.builder()
                .id(hotel.getId())
                .isHasImage(hotel.getIsHasImage() == 1 ? true : false)
                .averagePrice(hotel.getAveragePrice())
                .hotelName(hotel.getHotelName())
                .regionName(hotel.getRegion() != null ? hotel.getRegion().getName() : null) // Region의 name을 설정
                .imageUrls(hotel.getImageUrls())
                .averageReviewRate(hotel.getAverageReviewRate())
                .checkInDate(hotel.getCheckInDate())
                .checkOutDate(hotel.getCheckOutDate())
                .lat(hotel.getLat())
                .lng(hotel.getLng())
                .build();
    }
}
