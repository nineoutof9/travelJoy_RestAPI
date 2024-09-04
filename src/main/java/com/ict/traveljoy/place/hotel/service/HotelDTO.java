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
    private String address;
    private Float averageReviewRate; 
    private List<String> imageUrls;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public Hotel toEntity() {
        return Hotel.builder()
                .id(id)
                .isHasImage(isHasImage != null && isHasImage ? 1 : 0)
                .averagePrice(averagePrice)
                .hotelName(hotelName)
                .address(address)
                .imageUrls(imageUrls)
                .averageReviewRate(averageReviewRate)
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .build();
    }

    public static HotelDTO toDto(Hotel hotel) {
        return HotelDTO.builder()
                .id(hotel.getId())
                .isHasImage(hotel.getIsHasImage() == 1 ? true : false)
                .averagePrice(hotel.getAveragePrice())
                .hotelName(hotel.getHotelName())
                .address(hotel.getAddress())
                .imageUrls(hotel.getImageUrls())
                .averageReviewRate(hotel.getAverageReviewRate())
                .checkInDate(hotel.getCheckInDate())
                .checkOutDate(hotel.getCheckOutDate())
                .build();
    }
}
