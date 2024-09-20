package com.ict.traveljoy.favorite.service;

import java.time.LocalDateTime;
import java.util.List;

import com.ict.traveljoy.favorite.repository.Favorite;
import com.ict.traveljoy.place.food.repository.Food;
import com.ict.traveljoy.place.hotel.repository.Hotel;
import com.ict.traveljoy.place.sight.repository.Sight; 
import com.ict.traveljoy.place.region.repository.Region;
import com.ict.traveljoy.users.repository.Users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteDTO {

    private Long id;
    private Users user;
    private Long targetId;
    private Boolean isEvent;
    private Boolean isFood;
    private Boolean isSight;
    private Boolean isHotel;
    private Boolean isActive;
    private Boolean isDelete;
    private LocalDateTime createDate;
    private LocalDateTime deleteDate;
    private String hotelName;
    private List<String> hotelImageUrls;
    private String regionName;
    private String target;
    private String memo;
    private String foodName;
    private String foodAddress;
    private List<String> foodImageUrls;
    private String foodTel;
    private String foodWorkingTime;
    private Float lat;  
    private Float lng;  
    private String sightName;  
    private List<String> sightImageUrls;  
    private String sightAddress; 

    public Favorite toEntity() {
        switch (target.toLowerCase()) {
            case "event":
                isEvent = true;
                break;
            case "food":
                isFood = true;
                break;
            case "sight":
                isSight = true;
                break;
            case "hotel":
                isHotel = true;
                break;
            default:
                throw new IllegalArgumentException("Invalid target type: " + target);
        }

        return Favorite.builder()
                .id(id)
                .user(user)
                .targetId(targetId)
                .isEvent(isEvent == null || !isEvent ? 0 : 1)
                .isFood(isFood == null || !isFood ? 0 : 1)
                .isSight(isSight == null || !isSight ? 0 : 1)
                .isHotel(isHotel == null || !isHotel ? 0 : 1)
                .isActive(isActive == null || !isActive ? 0 : 1)
                .isDelete(isDelete == null || !isDelete ? 0 : 1)
                .createDate(createDate)
                .deleteDate(deleteDate)
                .memo(memo)
                .build();
    }

    public static FavoriteDTO toDTO(Favorite favorite) {
        String target = null;
        if (favorite.getIsEvent() == 1) target = "event";
        else if (favorite.getIsFood() == 1) target = "food";
        else if (favorite.getIsSight() == 1) target = "sight";
        else if (favorite.getIsHotel() == 1) target = "hotel";

        Hotel hotel = favorite.getHotel();
        String hotelName = hotel != null ? hotel.getHotelName() : null;
        List<String> hotelImageUrls = hotel != null ? hotel.getImageUrls() : null;
        String regionName = hotel != null && hotel.getRegion() != null ? hotel.getRegion().getName() : null;
        Float lat = hotel != null ? hotel.getLat() : null;
        Float lng = hotel != null ? hotel.getLng() : null;

        Food food = favorite.getFood();
        String foodName = food != null ? food.getFoodName() : null;
        String foodAddress = food != null ? food.getAddress() : null;
        List<String> foodImageUrls = food != null ? food.getImageUrls() : null;
        String foodTel = food != null ? food.getTel() : null;
        String foodWorkingTime = food != null ? food.getWorkingTime() : null;

        if (food != null) {
            lat = food.getLat();
            lng = food.getLng();
        }

        Sight sight = favorite.getSight(); 
        String sightName = sight != null ? sight.getSightName() : null; // 관광지 이름 가져오기
        String sightAddress = sight != null ? sight.getAddress() : null; // 관광지 주소 가져오기
        List<String> sightImageUrls = sight != null ? sight.getImageUrls() : null; // 관광지 이미지 URL 가져오기
        if (sight != null) {
            lat = sight.getLat(); 
            lng = sight.getLng(); 
        }

        return FavoriteDTO.builder()
                .id(favorite.getId())
                .user(favorite.getUser())
                .targetId(favorite.getTargetId())
                .lat(lat)  
                .lng(lng)  
                .isEvent(favorite.getIsEvent() == 1)
                .isFood(favorite.getIsFood() == 1)
                .isSight(favorite.getIsSight() == 1)
                .isHotel(favorite.getIsHotel() == 1)
                .isActive(favorite.getIsActive() == 1)
                .isDelete(favorite.getIsDelete() == 1)
                .createDate(favorite.getCreateDate())
                .deleteDate(favorite.getDeleteDate())
                .target(target)
                .hotelName(hotelName)
                .hotelImageUrls(hotelImageUrls)
                .regionName(regionName)
                .foodName(foodName)
                .foodAddress(foodAddress)
                .foodImageUrls(foodImageUrls)
                .foodTel(foodTel)
                .foodWorkingTime(foodWorkingTime)
                .sightName(sightName) 
                .sightImageUrls(sightImageUrls) 
                .sightAddress(sightAddress) 
                .memo(favorite.getMemo())
                .build();
    }
}
